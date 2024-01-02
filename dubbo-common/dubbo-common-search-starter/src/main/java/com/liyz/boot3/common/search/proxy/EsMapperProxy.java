package com.liyz.boot3.common.search.proxy;

import com.liyz.boot3.common.search.method.EsMapperMethod;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 20:08
 */
public class EsMapperProxy<T> implements InvocationHandler, Serializable {
    @Serial
    private static final long serialVersionUID = -8484138205139663445L;

    private final Class<T> mapperInterface;
    private final Map<Method, EsMapperMethodInvoker> methodCache;

    public EsMapperProxy(Class<T> mapperInterface, Map<Method, EsMapperMethodInvoker> methodCache) {
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
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
                    return null;
                }
            });
        }
        return invoker;
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
}
