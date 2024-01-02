package com.liyz.boot3.common.search.method;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/31 13:32
 */
public class SelectBatchIds extends AbstractEsMethod{

    public SelectBatchIds() {
        this(EsMethod.SELECT_BATCH_BY_IDS.getMethod());
    }

    public SelectBatchIds(String methodName) {
        super(methodName);
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
}
