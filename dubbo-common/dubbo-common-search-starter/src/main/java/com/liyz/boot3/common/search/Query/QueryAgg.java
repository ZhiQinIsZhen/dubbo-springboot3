package com.liyz.boot3.common.search.Query;

import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/11 15:52
 */
@Getter
@Setter
public class QueryAgg implements Serializable {
    @Serial
    private static final long serialVersionUID = -8880572400457149462L;

    private String colum;

    private Aggregation.Kind kind;

    private EsSortField esSortField;

    private SortOrder esSort;

    private int minDocCount;

    private int maxCount;

    public QueryAgg(String colum, Aggregation.Kind kind, EsSortField esSortField, SortOrder esSort, int minDocCount, int maxCount) {
        this.colum = colum;
        this.kind = kind;
        this.esSortField = esSortField;
        this.esSort = esSort;
        this.minDocCount = minDocCount;
        this.maxCount = maxCount;
    }
}
