package com.liyz.boot3.common.es.bidding;

import java.io.Serial;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 17:18
 */
public class EsMapperProxy<T> implements InvocationHandler, Serializable {
    @Serial
    private static final long serialVersionUID = 7963765568535099518L;

    private final Class<T> mapperInterface;
    private final Map<Method, EsMapperMethodInvoker> methodCache;

    private static final Constructor<MethodHandles.Lookup> lookupConstructor;
    private static final Method privateLookupInMethod;

    public EsMapperProxy(Class<T> mapperInterface, Map<Method, EsMapperMethodInvoker> methodCache) {
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return Object.class.equals(method.getDeclaringClass()) ? method.invoke(this, args) : this.cachedInvoker(method).invoke(proxy, method, args);
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    static {
        Method privateLookupIn;
        try {
            privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException e) {
            privateLookupIn = null;
        }

        privateLookupInMethod = privateLookupIn;
        Constructor<MethodHandles.Lookup> lookup = null;
        if (privateLookupInMethod == null) {
            try {
                lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                lookup.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException("There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e);
            } catch (Throwable t) {
                lookup = null;
            }
        }

        lookupConstructor = lookup;
    }

    interface EsMapperMethodInvoker {
        Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
    }

    private EsMapperMethodInvoker cachedInvoker(Method method) throws Throwable {
        try {
            EsMapperMethodInvoker invoker = this.methodCache.get(method);
            if (invoker == null) {
                invoker = this.methodCache.computeIfAbsent(method, (m) -> {
                    if (!m.isDefault()) {
                        return new PlainMethodInvoker(new EsMapperMethod(this.mapperInterface, method));
                    } else {
                        try {
                            return new DefaultMethodInvoker(this.getMethodHandleJava9(method));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            }
            return invoker;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private MethodHandle getMethodHandleJava9(Method method) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Class<?> declaringClass = method.getDeclaringClass();
        return ((MethodHandles.Lookup)privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup())).findSpecial(declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
    }

    private static class PlainMethodInvoker implements EsMapperMethodInvoker {
        private final EsMapperMethod mapperMethod;

        public PlainMethodInvoker(EsMapperMethod mapperMethod) {
            this.mapperMethod = mapperMethod;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.mapperMethod.execute(args);
        }
    }

    private static class DefaultMethodInvoker implements EsMapperMethodInvoker {
        private final MethodHandle methodHandle;

        public DefaultMethodInvoker(MethodHandle methodHandle) {
            this.methodHandle = methodHandle;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return this.methodHandle.bindTo(proxy).invokeWithArguments(args);
        }
    }
}
