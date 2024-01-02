package com.liyz.boot3.common.search.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 19:59
 */
public class EsMapperProxyFactory<T> {

    private final Class<T> mapperInterface;
    private final Map<Method, EsMapperProxy.EsMapperMethodInvoker> methodCache = new ConcurrentHashMap();

    public EsMapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance() {
        EsMapperProxy<T> mapperProxy = new EsMapperProxy(this.mapperInterface, this.methodCache);
        return this.newInstance(mapperProxy);
    }

    public T newInstance(EsMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
    }
}
