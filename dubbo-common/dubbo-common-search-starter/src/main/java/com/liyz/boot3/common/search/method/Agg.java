package com.liyz.boot3.common.search.method;

import co.elastic.clients.elasticsearch.core.SearchRequest;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/11 16:08
 */
public class Agg extends AbstractEsMethod {

    public Agg() {
        this(EsMethod.AGG.getMethod());
    }

    public Agg(String methodName) {
        super(methodName);
    }

    @Override
    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = super.buildRequest(args);
        return builder.size(0);
    }
}
