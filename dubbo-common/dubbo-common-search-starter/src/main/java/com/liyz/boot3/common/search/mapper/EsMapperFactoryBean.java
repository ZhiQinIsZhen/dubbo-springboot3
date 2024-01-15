package com.liyz.boot3.common.search.mapper;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 19:45
 */
public class EsMapperFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    private final Class<T> mapperInterface;

    private final EsMapperRegistry esMapperRegistry = EsMapperRegistry.getInstance();

    public EsMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    @Override
    public T getObject() throws Exception {
        return this.esMapperRegistry.getEsMapper(this.mapperInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.mapperInterface;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (!this.esMapperRegistry.hasMapper(this.mapperInterface)) {
            this.esMapperRegistry.addMapper(this.mapperInterface);
        }

    }
}
