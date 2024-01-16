package com.liyz.boot3.common.search.util;

import com.liyz.boot3.common.search.method.*;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/31 12:59
 */
@UtilityClass
public class GlobalEsCacheUtil {

    private static final Map<String, AbstractEsMethod> METHOD_MAP = new ConcurrentHashMap<>(256);

    public static AbstractEsMethod getEsMethod(String methodName) {
        return METHOD_MAP.get(methodName);
    }

    public static void initEsMethodMap() {
        addEsMethod(new SelectById());
        addEsMethod(new SelectBatchIds());
        addEsMethod(new SelectOne());
        addEsMethod(new SelectPage());
        addEsMethod(new Agg());
    }

    public static void addEsMethod(AbstractEsMethod esMethod) {
        METHOD_MAP.putIfAbsent(esMethod.getMethodName(), esMethod);
    }
}
