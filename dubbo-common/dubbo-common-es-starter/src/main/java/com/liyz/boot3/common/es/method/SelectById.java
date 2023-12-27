package com.liyz.boot3.common.es.method;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.liyz.boot3.common.es.mapper.EsMethod;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/26 11:13
 */
public class SelectById extends AbstractEsMethod {

    @Override
    public Object execute(Class<?> mapperInterface, Method method, Object[] args) {
        Object object = super.execute(mapperInterface, method, args);
        if (object == null) {
            return null;
        }
        if (object instanceof List<?> list) {
            return list.get(0);
        }
        return object;
    }

    @Override
    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = super.buildRequest(args);
        return builder
                .query(q -> q.ids(IdsQuery.of(idq -> idq.values(Arrays.stream(args).map(Object::toString).collect(Collectors.toList())))))
                .size(1);
    }

    @Override
    public EsMethod getEsMethod() {
        return EsMethod.SELECT_BY_ID;
    }
}
