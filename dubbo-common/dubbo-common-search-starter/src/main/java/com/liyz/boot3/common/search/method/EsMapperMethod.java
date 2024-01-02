package com.liyz.boot3.common.search.method;

import com.liyz.boot3.common.search.util.GlobalEsCacheUtil;

import java.lang.reflect.Method;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 20:10
 */
public class EsMapperMethod {

    private final Class<?> mapperInterface;
    private final Method method;

    public EsMapperMethod(Class<?> mapperInterface, Method method) {
        this.mapperInterface = mapperInterface;
        this.method = method;
    }

    public Object execute(Object[] args) {
        String methodName = method.getName();
        EsMethod esMethod = EsMethod.getByMethod(methodName);
        if (esMethod == null) {
            throw new IllegalArgumentException("' " + methodName + "' not bind EsMethod");
        }
        AbstractEsMethod iEsMethod = GlobalEsCacheUtil.getEsMethod(methodName);
        if (iEsMethod == null) {
            throw new IllegalArgumentException("' " + methodName + "' not have IEsMethod instance");
        }
        return iEsMethod.execute(mapperInterface, method, args);
    }
}
