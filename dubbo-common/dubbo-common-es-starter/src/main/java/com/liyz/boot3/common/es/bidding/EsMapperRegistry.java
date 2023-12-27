package com.liyz.boot3.common.es.bidding;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 16:51
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

    private final Map<Class<?>, EsMapperProxyFactory<?>> knownMappers = new HashMap();

    public <T> T getEsMapper(Class<T> type) {
        EsMapperProxyFactory<T> mapperProxyFactory = (EsMapperProxyFactory<T>) this.knownMappers.get(type);
        if (mapperProxyFactory == null) {
            mapperProxyFactory = (EsMapperProxyFactory<T>) knownMappers.entrySet()
                    .stream()
                    .filter(t -> t.getKey().getName().equals(type.getName()))
                    .findFirst()
                    .map(Map.Entry::getValue)
                    .orElseThrow(() -> new RuntimeException("Type " + type + " is not known to the MapperRegistry."));
        }
        try {
            return mapperProxyFactory.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: ", e);
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface()) {
            if (hasMapper(type)) {
                return;
            }
            knownMappers.put(type, new EsMapperProxyFactory<>(type));
        }
    }
}
