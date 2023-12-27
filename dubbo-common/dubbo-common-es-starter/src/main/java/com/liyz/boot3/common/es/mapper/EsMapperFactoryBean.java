package com.liyz.boot3.common.es.mapper;

import com.liyz.boot3.common.es.bidding.EsMapperRegistry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 16:50
 */
public class EsMapperFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    private Class<T> mapperInterface;
    private EsMapperRegistry esMapperRegistry;

    public EsMapperFactoryBean() {
    }

    public EsMapperFactoryBean(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public void setMapperInterface(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public EsMapperRegistry getEsMapperRegistry() {
        return esMapperRegistry;
    }

    public void setEsMapperRegistry(EsMapperRegistry esMapperRegistry) {
        if (esMapperRegistry == null) {
            esMapperRegistry = EsMapperRegistry.getInstance();
        }
        this.esMapperRegistry = esMapperRegistry;
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
        this.setEsMapperRegistry(EsMapperRegistry.getInstance());
        if (!esMapperRegistry.hasMapper(this.mapperInterface)) {
            esMapperRegistry.addMapper(this.mapperInterface);
        }
    }
}
