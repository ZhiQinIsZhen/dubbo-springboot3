package com.liyz.boot3.common.search.method;

import co.elastic.clients.elasticsearch.core.SearchRequest;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 16:43
 */
public class SelectOne extends AbstractEsMethod {

    public SelectOne() {
        this(EsMethod.SELECT_ONE.getMethod());
    }

    public SelectOne(String methodName) {
        super(methodName);
    }

    @Override
    protected SearchRequest.Builder buildRequest(Object[] args) {
        SearchRequest.Builder builder = super.buildRequest(args);
        return builder.size(1);
    }
}
