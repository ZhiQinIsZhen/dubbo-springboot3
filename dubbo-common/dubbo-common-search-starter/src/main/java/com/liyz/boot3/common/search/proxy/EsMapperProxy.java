package com.liyz.boot3.common.search.proxy;

import com.liyz.boot3.common.search.exception.SearchException;
import com.liyz.boot3.common.search.exception.SearchExceptionCodeEnum;
import com.liyz.boot3.common.search.method.EsMapperMethod;
import lombok.extern.slf4j.Slf4j;

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
 * @date 2023/12/30 20:08
 */
@Slf4j
public class EsMapperProxy<T> implements InvocationHandler, Serializable {
    @Serial
    private static final long serialVersionUID = -8484138205139663445L;

    private static final Method privateLookupInMethod;
    private final Class<T> mapperInterface;
    private final Map<Method, EsMapperMethodInvoker> methodCache;

    public EsMapperProxy(Class<T> mapperInterface, Map<Method, EsMapperMethodInvoker> methodCache) {
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    static {
        Method privateLookupIn;
        try {
            privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException e) {
            privateLookupIn = null;
        }
        privateLookupInMethod = privateLookupIn;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return Object.class.equals(method.getDeclaringClass()) ?
                method.invoke(this, args) : this.cachedInvoker(method).invoke(proxy, method, args);
    }

    private EsMapperMethodInvoker cachedInvoker(Method method) {
        EsMapperMethodInvoker invoker = this.methodCache.get(method);
        if (invoker == null) {
            invoker = this.methodCache.computeIfAbsent(method, (m) -> {
                if (!m.isDefault()) {
                    return new PlainMethodInvoker(new EsMapperMethod(this.mapperInterface, method));
                } else {
                    try {
                        return new DefaultMethodInvoker(getMethodHandleJava9(method));
                    } catch (Exception e) {
                        log.error(SearchExceptionCodeEnum.DEFAULT_INVOKER_FAIL.getMessage(), e);
                        throw new SearchException(SearchExceptionCodeEnum.DEFAULT_INVOKER_FAIL);
                    }
                }
            });
        }
        return invoker;
    }

    private MethodHandle getMethodHandleJava9(Method method)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Class<?> declaringClass = method.getDeclaringClass();
        return ((MethodHandles.Lookup) privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup())).findSpecial(
                declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()),
                declaringClass);
    }

    interface EsMapperMethodInvoker {
        Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
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
            return methodHandle.bindTo(proxy).invokeWithArguments(args);
        }
    }
}
