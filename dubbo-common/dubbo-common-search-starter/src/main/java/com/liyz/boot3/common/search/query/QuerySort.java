package com.liyz.boot3.common.search.query;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 17:49
 */
@Getter
@Setter
public class QuerySort implements Serializable {
    @Serial
    private static final long serialVersionUID = -7983820262285665051L;

    private SortOrder esSort;

    private String colum;

    public QuerySort(SortOrder esSort, String colum) {
        this.esSort = esSort;
        this.colum = colum;
    }
}
