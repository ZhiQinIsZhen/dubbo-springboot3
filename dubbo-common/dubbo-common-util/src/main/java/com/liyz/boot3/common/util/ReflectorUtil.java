package com.liyz.boot3.common.util;

import cn.hutool.core.util.ReflectUtil;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 18:47
 */
@UtilityClass
public class ReflectorUtil extends ReflectUtil {

    /**
     * type转化为class
     *
     * @param srcType type
     * @return class
     */
    public static Class<?> typeToClass(Type srcType) {
        Class<?> result = Object.class;
        if (srcType instanceof Class<?> clazz) {
            result = clazz;
        } else if (srcType instanceof ParameterizedType parameterizedType) {
            result = (Class<?>) parameterizedType.getRawType();
        } else if (srcType instanceof GenericArrayType genericArrayType) {
            Type componentType = genericArrayType.getGenericComponentType();
            if (componentType instanceof Class<?> clazz) {
                result = Array.newInstance(clazz, 0).getClass();
            } else {
                Class<?> componentClass = typeToClass(componentType);
                result = Array.newInstance(componentClass, 0).getClass();
            }
        }
        return result;
    }

    /**
     * 解析返回class
     *
     * @param method 调用方法
     * @param srcType type
     * @return 方法返回class
     */
    public static Class<?> resolveReturnClass(Method method, Type srcType) {
        return typeToClass(resolveReturnType(method, srcType));
    }

    /**
     * 解析返回type
     *
     * @param method 调用方法
     * @param srcType type
     * @return 方法返回type
     */
    public static Type resolveReturnType(Method method, Type srcType) {
        Type returnType = method.getGenericReturnType();
        Class<?> declaringClass = method.getDeclaringClass();
        return resolveType(returnType, srcType, declaringClass);
    }

    private static Type resolveType(Type type, Type srcType, Class<?> declaringClass) {
        if (type instanceof TypeVariable<?> typeVariable) {
            return resolveTypeVar(typeVariable, srcType, declaringClass);
        }
        if (type instanceof ParameterizedType parameterizedType) {
            return resolveParameterizedType(parameterizedType, srcType, declaringClass);
        } else if (type instanceof GenericArrayType genericArrayType) {
            return resolveGenericArrayType(genericArrayType, srcType, declaringClass);
        } else {
            return type;
        }
    }

    private static Type resolveTypeVar(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass) {
        Type result;
        Class<?> clazz;
        if (srcType instanceof Class<?> srcClazz) {
            clazz = srcClazz;
        } else if (srcType instanceof ParameterizedType parameterizedType) {
            clazz = (Class<?>) parameterizedType.getRawType();
        } else {
            throw new RemoteServiceException(CommonExceptionCodeEnum.REFLECTOR_FAIL.getCode(),
                    "The 2nd arg must be Class or ParameterizedType, but was: " + srcType.getClass());
        }

        if (clazz == declaringClass) {
            Type[] bounds = typeVar.getBounds();
            if (bounds.length > 0) {
                return bounds[0];
            }
            return Object.class;
        }

        Type superclass = clazz.getGenericSuperclass();
        result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superclass);
        if (result != null) {
            return result;
        }

        Type[] superInterfaces = clazz.getGenericInterfaces();
        for (Type superInterface : superInterfaces) {
            result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superInterface);
            if (result != null) {
                return result;
            }
        }
        return Object.class;
    }

    private static ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType, Type srcType,
                                                              Class<?> declaringClass) {
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        Type[] typeArgs = parameterizedType.getActualTypeArguments();
        Type[] args = new Type[typeArgs.length];
        for (int i = 0; i < typeArgs.length; i++) {
            if (typeArgs[i] instanceof TypeVariable) {
                args[i] = resolveTypeVar((TypeVariable<?>) typeArgs[i], srcType, declaringClass);
            } else if (typeArgs[i] instanceof ParameterizedType) {
                args[i] = resolveParameterizedType((ParameterizedType) typeArgs[i], srcType, declaringClass);
            } else if (typeArgs[i] instanceof WildcardType) {
                args[i] = resolveWildcardType((WildcardType) typeArgs[i], srcType, declaringClass);
            } else {
                args[i] = typeArgs[i];
            }
        }
        return new TypeParameterResolverUtil.ParameterizedTypeImpl(rawType, null, args);
    }

    private static Type resolveWildcardType(WildcardType wildcardType, Type srcType, Class<?> declaringClass) {
        Type[] lowerBounds = resolveWildcardTypeBounds(wildcardType.getLowerBounds(), srcType, declaringClass);
        Type[] upperBounds = resolveWildcardTypeBounds(wildcardType.getUpperBounds(), srcType, declaringClass);
        return new WildcardTypeImpl(lowerBounds, upperBounds);
    }

    private static Type[] resolveWildcardTypeBounds(Type[] bounds, Type srcType, Class<?> declaringClass) {
        Type[] result = new Type[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            if (bounds[i] instanceof TypeVariable<?> typeVariable) {
                result[i] = resolveTypeVar(typeVariable, srcType, declaringClass);
            } else if (bounds[i] instanceof ParameterizedType parameterizedType) {
                result[i] = resolveParameterizedType(parameterizedType, srcType, declaringClass);
            } else if (bounds[i] instanceof WildcardType wildcardType) {
                result[i] = resolveWildcardType(wildcardType, srcType, declaringClass);
            } else {
                result[i] = bounds[i];
            }
        }
        return result;
    }

    private static Type scanSuperTypes(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass, Class<?> clazz,
                                       Type superclass) {
        if (superclass instanceof ParameterizedType parameterizedType) {
            Class<?> parentAsClass = (Class<?>) parameterizedType.getRawType();
            TypeVariable<?>[] parentTypeVars = parentAsClass.getTypeParameters();
            if (srcType instanceof ParameterizedType srcParameterizedType) {
                parameterizedType = translateParentTypeVars(srcParameterizedType, clazz, parameterizedType);
            }
            if (declaringClass == parentAsClass) {
                for (int i = 0; i < parentTypeVars.length; i++) {
                    if (typeVar.equals(parentTypeVars[i])) {
                        return parameterizedType.getActualTypeArguments()[i];
                    }
                }
            }
            if (declaringClass.isAssignableFrom(parentAsClass)) {
                return resolveTypeVar(typeVar, parameterizedType, declaringClass);
            }
        } else if (superclass instanceof Class && declaringClass.isAssignableFrom((Class<?>) superclass)) {
            return resolveTypeVar(typeVar, superclass, declaringClass);
        }
        return null;
    }

    private static ParameterizedType translateParentTypeVars(ParameterizedType srcType, Class<?> srcClass,
                                                             ParameterizedType parentType) {
        Type[] parentTypeArgs = parentType.getActualTypeArguments();
        Type[] srcTypeArgs = srcType.getActualTypeArguments();
        TypeVariable<?>[] srcTypeVars = srcClass.getTypeParameters();
        Type[] newParentArgs = new Type[parentTypeArgs.length];
        boolean noChange = true;
        for (int i = 0; i < parentTypeArgs.length; i++) {
            if (parentTypeArgs[i] instanceof TypeVariable) {
                for (int j = 0; j < srcTypeVars.length; j++) {
                    if (srcTypeVars[j].equals(parentTypeArgs[i])) {
                        noChange = false;
                        newParentArgs[i] = srcTypeArgs[j];
                    }
                }
            } else {
                newParentArgs[i] = parentTypeArgs[i];
            }
        }
        return noChange ? parentType : new ParameterizedTypeImpl((Class<?>) parentType.getRawType(), null, newParentArgs);
    }

    private static Type resolveGenericArrayType(GenericArrayType genericArrayType, Type srcType,
                                                Class<?> declaringClass) {
        Type componentType = genericArrayType.getGenericComponentType();
        Type resolvedComponentType = null;
        if (componentType instanceof TypeVariable<?> typeVariable) {
            resolvedComponentType = resolveTypeVar(typeVariable, srcType, declaringClass);
        } else if (componentType instanceof GenericArrayType genericArrayType1) {
            resolvedComponentType = resolveGenericArrayType(genericArrayType1, srcType, declaringClass);
        } else if (componentType instanceof ParameterizedType parameterizedType) {
            resolvedComponentType = resolveParameterizedType(parameterizedType, srcType, declaringClass);
        }
        if (resolvedComponentType instanceof Class clazz) {
            return Array.newInstance(clazz, 0).getClass();
        }
        return new GenericArrayTypeImpl(resolvedComponentType);
    }

    public static class WildcardTypeImpl implements WildcardType {
        private final Type[] lowerBounds;

        private final Type[] upperBounds;

        WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
            this.lowerBounds = lowerBounds;
            this.upperBounds = upperBounds;
        }

        @Override
        public Type[] getLowerBounds() {
            return lowerBounds;
        }

        @Override
        public Type[] getUpperBounds() {
            return upperBounds;
        }
    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class<?> rawType;

        private final Type ownerType;

        private final Type[] actualTypeArguments;

        public ParameterizedTypeImpl(Class<?> rawType, Type ownerType, Type[] actualTypeArguments) {
            this.rawType = rawType;
            this.ownerType = ownerType;
            this.actualTypeArguments = actualTypeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public String toString() {
            return "ParameterizedTypeImpl [rawType=" + rawType + ", ownerType=" + ownerType + ", actualTypeArguments="
                    + Arrays.toString(actualTypeArguments) + "]";
        }
    }

    public static class GenericArrayTypeImpl implements GenericArrayType {
        private final Type genericComponentType;

        GenericArrayTypeImpl(Type genericComponentType) {
            this.genericComponentType = genericComponentType;
        }

        @Override
        public Type getGenericComponentType() {
            return genericComponentType;
        }
    }
}
