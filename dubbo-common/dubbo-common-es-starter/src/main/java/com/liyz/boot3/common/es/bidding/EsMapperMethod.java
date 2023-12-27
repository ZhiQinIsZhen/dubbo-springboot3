package com.liyz.boot3.common.es.bidding;

import com.liyz.boot3.common.es.mapper.EsMethod;
import com.liyz.boot3.common.es.method.IEsMethod;
import com.liyz.boot3.common.es.method.SelectBatchIds;
import com.liyz.boot3.common.es.method.SelectById;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 17:19
 */
public class EsMapperMethod {

    private static final Map<EsMethod, IEsMethod> METHOD_MAP = new EnumMap<>(EsMethod.class);

    static {
        put(new SelectById());
        put(new SelectBatchIds());
    }

    private static void put(IEsMethod esMethod) {
        METHOD_MAP.put(esMethod.getEsMethod(), esMethod);
    }

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
        IEsMethod iEsMethod = METHOD_MAP.get(esMethod);
        if (iEsMethod == null) {
            throw new IllegalArgumentException("' " + methodName + "' not have IEsMethod instance");
        }
        return iEsMethod.execute(mapperInterface, method, args);
    }


}
