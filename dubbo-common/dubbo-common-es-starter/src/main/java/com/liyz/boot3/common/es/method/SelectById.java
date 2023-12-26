package com.liyz.boot3.common.es.method;

import com.liyz.boot3.common.es.mapper.EsMethod;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/26 11:13
 */
public class SelectById extends AbstractEsMethod {

    @Override
    public Object execute(Class<?> mapperInterface, Object[] args) {
        Object object = super.execute(mapperInterface, args);
        if (object == null) {
            return null;
        }
        if (object instanceof List<?> list) {
            return list.get(0);
        }
        return object;
    }

    @Override
    public EsMethod getEsMethod() {
        return EsMethod.SELECT_BY_ID;
    }
}
