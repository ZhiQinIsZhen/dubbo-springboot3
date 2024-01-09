package com.liyz.boot3.common.search.method;

import co.elastic.clients.elasticsearch._types.query_dsl.IdsQuery;
import co.elastic.clients.elasticsearch.core.SearchRequest;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/31 13:31
 */
public class SelectById extends AbstractEsMethod {

    public SelectById() {
        this(EsMethod.SELECT_BY_ID.getMethod());
    }

    public SelectById(String methodName) {
        super(methodName);
    }

    @Override
    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = super.buildRequest(args);
        return builder
                .query(q -> q.ids(IdsQuery.of(idq -> idq.values(args[0].toString()))))
                .size(1);
    }
}
