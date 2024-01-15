package com.liyz.boot3.common.search.mapper;

import com.liyz.boot3.common.search.proxy.EsMapperProxyFactory;
import com.liyz.boot3.common.search.util.IndexInfoUtil;
import com.liyz.boot3.common.util.resolver.TypeParameterResolver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 19:55
 */
public class EsMapperRegistry {

    private static volatile EsMapperRegistry instance;

    public static EsMapperRegistry getInstance() {
        if (instance == null) {
            synchronized (EsMapperRegistry.class) {
                if (instance == null) {
                    instance = new EsMapperRegistry();
                }
            }
        }
        return instance;
    }

    private EsMapperRegistry() {
    }

    private final Map<Class<?>, EsMapperProxyFactory<?>> knownMappers = new ConcurrentHashMap<>();

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                return;
            }
            //开始缓存
            knownMappers.put(type, new EsMapperProxyFactory<>(type));
            Class<?> modelClass = (Class<?>) TypeParameterResolver.resolveClassIndexedParameter(type, EsMapper.class, 0);
            IndexInfoUtil.getIndexInfo(modelClass);
        }
    }

    public <T> T getEsMapper(Class<T> type) {
        EsMapperProxyFactory<T> mapperProxyFactory = (EsMapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: ", e);
        }
    }
}
