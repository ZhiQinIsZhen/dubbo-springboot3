package com.liyz.boot3.common.es.bidding;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 16:52
 */
public class EsMapperProxyFactory<T> {

    private final Class<T> mapperInterface;
    private final Map<Method, EsMapperProxy.EsMapperMethodInvoker> methodCache = new ConcurrentHashMap();

    public EsMapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public Map<Method, EsMapperProxy.EsMapperMethodInvoker> getMethodCache() {
        return methodCache;
    }

    protected T newInstance(EsMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(this.mapperInterface.getClassLoader(), new Class[]{this.mapperInterface}, mapperProxy);
    }

    protected T newInstance() {
        EsMapperProxy<T> mapperProxy = new EsMapperProxy(this.mapperInterface, this.methodCache);
        return this.newInstance(mapperProxy);
    }
}
