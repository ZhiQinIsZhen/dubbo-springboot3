package com.liyz.boot3.common.es.bidding;

import com.liyz.boot3.common.es.method.SelectById;

import java.lang.reflect.Method;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 17:19
 */
public class EsMapperMethod {

    private final Class<?> mapperInterface;
    private final Method method;

    public EsMapperMethod(Class<?> mapperInterface, Method method) {
        this.mapperInterface = mapperInterface;
        this.method = method;
    }

    public Object execute(Object[] args) {
        return new SelectById().execute(mapperInterface, args);
    }
}
