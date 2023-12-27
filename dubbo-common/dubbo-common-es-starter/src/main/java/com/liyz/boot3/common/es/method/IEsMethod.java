package com.liyz.boot3.common.es.method;

import com.liyz.boot3.common.es.mapper.EsMethod;

import java.lang.reflect.Method;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/26 11:27
 */
public interface IEsMethod {

    Object execute(Class<?> mapperInterface, Method method, Object[] args);

    EsMethod getEsMethod();
}
