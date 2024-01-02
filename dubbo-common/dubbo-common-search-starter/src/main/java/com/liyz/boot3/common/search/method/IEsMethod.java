package com.liyz.boot3.common.search.method;

import java.lang.reflect.Method;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 21:13
 */
public interface IEsMethod {

    Object execute(Class<?> mapperInterface, Method method, Object[] args);
}
