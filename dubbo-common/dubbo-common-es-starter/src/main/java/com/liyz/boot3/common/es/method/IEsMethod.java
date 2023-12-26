package com.liyz.boot3.common.es.method;

import com.liyz.boot3.common.es.mapper.EsMethod;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/26 11:27
 */
public interface IEsMethod {

    Object execute(Class<?> mapperInterface, Object[] args);

    EsMethod getEsMethod();
}
