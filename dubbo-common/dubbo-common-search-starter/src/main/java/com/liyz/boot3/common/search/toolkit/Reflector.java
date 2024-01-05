package com.liyz.boot3.common.search.toolkit;

import com.liyz.boot3.common.util.PropertyUtil;
import com.liyz.boot3.common.util.TypeParameterResolverUtil;
import org.springframework.util.MethodInvoker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.*;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 15:36
 */
public class Reflector {

    private static final MethodHandle isRecordMethodHandle = getIsRecordMethodHandle();
    private final Class<?> type;
    private Constructor<?> defaultConstructor;
    private final String[] readablePropertyNames;
    private final String[] writablePropertyNames;
    private final Map<String, Method> setMethods = new HashMap<>();
    private final Map<String, Method> getMethods = new HashMap<>();
    private final Map<String, Class<?>> setTypes = new HashMap<>();
    private final Map<String, Class<?>> getTypes = new HashMap<>();

    private final Map<String, String> caseInsensitivePropertyMap = new HashMap<>();

    public Reflector(Class<?> clazz) {
        this.type = clazz;
        addDefaultConstructor(clazz);
        Method[] classMethods = getClassMethods(clazz);
        if (isRecord(type)) {
            addRecordGetMethods(classMethods);
        } else {
            addGetMethods(classMethods);
            addSetMethods(classMethods);
        }
        readablePropertyNames = getMethods.keySet().toArray(new String[0]);
        writablePropertyNames = setMethods.keySet().toArray(new String[0]);
        for (String propName : readablePropertyNames) {
            caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
        }
        for (String propName : writablePropertyNames) {
            caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
        }
    }

    /**
     * Gets the type for a property setter.
     *
     * @param propertyName
     *          - the name of the property
     *
     * @return The Class of the property setter
     */
    public Class<?> getSetterType(String propertyName) {
        Class<?> clazz = setTypes.get(propertyName);
        if (clazz == null) {
            throw new RuntimeException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    /**
     * Gets the type for a property getter.
     *
     * @param propertyName
     *          - the name of the property
     *
     * @return The Class of the property getter
     */
    public Class<?> getGetterType(String propertyName) {
        Class<?> clazz = getTypes.get(propertyName);
        if (clazz == null) {
            throw new RuntimeException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
        }
        return clazz;
    }

    private void addSetMethods(Method[] methods) {
        Map<String, List<Method>> conflictingSetters = new HashMap<>();
        Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 1 && PropertyUtil.isSetter(m.getName()))
                .forEach(m -> addMethodConflict(conflictingSetters, PropertyUtil.methodToProperty(m.getName()), m));
        resolveSetterConflicts(conflictingSetters);
    }

    private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
        for (Map.Entry<String, List<Method>> entry : conflictingSetters.entrySet()) {
            String propName = entry.getKey();
            List<Method> setters = entry.getValue();
            Class<?> getterType = getTypes.get(propName);
            boolean isSetterAmbiguous = false;
            Method match = null;
            for (Method setter : setters) {
                if (setter.getParameterTypes()[0].equals(getterType)) {
                    // should be the best match
                    match = setter;
                    break;
                }
                if (!isSetterAmbiguous) {
                    match = pickBetterSetter(match, setter, propName);
                    isSetterAmbiguous = match == null;
                }
            }
            if (match != null) {
                addSetMethod(propName, match);
            }
        }
    }

    private void addSetMethod(String name, Method method) {
        setMethods.put(name, method);
        Type[] paramTypes = TypeParameterResolverUtil.resolveParamTypes(method, type);
        setTypes.put(name, typeToClass(paramTypes[0]));
    }

    private Method pickBetterSetter(Method setter1, Method setter2, String property) {
        if (setter1 == null) {
            return setter2;
        }
        Class<?> paramType1 = setter1.getParameterTypes()[0];
        Class<?> paramType2 = setter2.getParameterTypes()[0];
        if (paramType1.isAssignableFrom(paramType2)) {
            return setter2;
        }
        if (paramType2.isAssignableFrom(paramType1)) {
            return setter1;
        }
        setMethods.put(property, setter1);
        Type[] paramTypes = TypeParameterResolverUtil.resolveParamTypes(setter1, type);
        setTypes.put(property, typeToClass(paramTypes[0]));
        return null;
    }

    private void addDefaultConstructor(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Arrays.stream(constructors).filter(constructor -> constructor.getParameterTypes().length == 0).findAny()
                .ifPresent(constructor -> this.defaultConstructor = constructor);
    }

    private Method[] getClassMethods(Class<?> clazz) {
        Map<String, Method> uniqueMethods = new HashMap<>();
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());

            // we also need to look for interface methods -
            // because the class may be abstract
            Class<?>[] interfaces = currentClass.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addUniqueMethods(uniqueMethods, anInterface.getMethods());
            }

            currentClass = currentClass.getSuperclass();
        }

        Collection<Method> methods = uniqueMethods.values();

        return methods.toArray(new Method[0]);
    }

    private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
        for (Method currentMethod : methods) {
            if (!currentMethod.isBridge()) {
                String signature = getSignature(currentMethod);
                // check to see if the method is already known
                // if it is known, then an extended class must have
                // overridden a method
                if (!uniqueMethods.containsKey(signature)) {
                    uniqueMethods.put(signature, currentMethod);
                }
            }
        }
    }

    private String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        Class<?> returnType = method.getReturnType();
        sb.append(returnType.getName()).append('#');
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(i == 0 ? ':' : ',').append(parameters[i].getName());
        }
        return sb.toString();
    }

    /**
     * Class.isRecord() alternative for Java 15 and older.
     */
    private static boolean isRecord(Class<?> clazz) {
        try {
            return isRecordMethodHandle != null && (boolean) isRecordMethodHandle.invokeExact(clazz);
        } catch (Throwable e) {
            throw new RuntimeException("Failed to invoke 'Class.isRecord()'.", e);
        }
    }

    private void addRecordGetMethods(Method[] methods) {
        Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 0)
                .forEach(m -> addGetMethod(m.getName(), m, false));
    }

    private void addGetMethods(Method[] methods) {
        Map<String, List<Method>> conflictingGetters = new HashMap<>();
        Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 0 && PropertyUtil.isGetter(m.getName()))
                .forEach(m -> addMethodConflict(conflictingGetters, PropertyUtil.methodToProperty(m.getName()), m));
        resolveGetterConflicts(conflictingGetters);
    }

    private void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method) {
        if (isValidPropertyName(name)) {
            List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
            list.add(method);
        }
    }

    private boolean isValidPropertyName(String name) {
        return (!name.startsWith("$") && !"serialVersionUID".equals(name) && !"class".equals(name));
    }

    private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
        for (Map.Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
            Method winner = null;
            String propName = entry.getKey();
            boolean isAmbiguous = false;
            for (Method candidate : entry.getValue()) {
                if (winner == null) {
                    winner = candidate;
                    continue;
                }
                Class<?> winnerType = winner.getReturnType();
                Class<?> candidateType = candidate.getReturnType();
                if (candidateType.equals(winnerType)) {
                    if (!boolean.class.equals(candidateType)) {
                        isAmbiguous = true;
                        break;
                    }
                    if (candidate.getName().startsWith("is")) {
                        winner = candidate;
                    }
                } else if (candidateType.isAssignableFrom(winnerType)) {
                    // OK getter type is descendant
                } else if (winnerType.isAssignableFrom(candidateType)) {
                    winner = candidate;
                } else {
                    isAmbiguous = true;
                    break;
                }
            }
            addGetMethod(propName, winner, isAmbiguous);
        }
    }

    private void addGetMethod(String name, Method method, boolean isAmbiguous) {
        getMethods.put(name, method);
        Type returnType = TypeParameterResolverUtil.resolveReturnType(method, type);
        getTypes.put(name, typeToClass(returnType));
    }

    private Class<?> typeToClass(Type src) {
        Class<?> result = null;
        if (src instanceof Class) {
            result = (Class<?>) src;
        } else if (src instanceof ParameterizedType) {
            result = (Class<?>) ((ParameterizedType) src).getRawType();
        } else if (src instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) src).getGenericComponentType();
            if (componentType instanceof Class) {
                result = Array.newInstance((Class<?>) componentType, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        if (result == null) {
            result = Object.class;
        }
        return result;
    }

    private static MethodHandle getIsRecordMethodHandle() {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(boolean.class);
        try {
            return lookup.findVirtual(Class.class, "isRecord", mt);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            return null;
        }
    }
}
