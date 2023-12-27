package com.liyz.boot3.common.es.method;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.liyz.boot3.common.es.mapper.EsMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/27 14:04
 */
public class SelectBatchIds extends AbstractEsMethod{

    @Override
    public Object execute(Class<?> mapperInterface, Method method, Object[] args) {
        Object object = super.execute(mapperInterface, method, args);
        if (object == null) {
            return new ArrayList<>();
        }
        if (object instanceof List<?> list) {
            return list;
        }
        return List.of(object);
    }

    @Override
    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = super.buildRequest(args);
        if (args != null && args[0] instanceof Collection<?> list) {
            builder = builder
                        .query(q -> q.ids(IdsQuery.of(idq -> idq.values(list.stream().map(Object::toString).collect(Collectors.toList())))))
                        .size(list.size());
        }
        return builder;
    }

    @Override
    public EsMethod getEsMethod() {
        return EsMethod.SELECT_BATCH_BY_IDS;
    }
}
