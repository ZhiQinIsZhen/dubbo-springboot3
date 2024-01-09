package com.liyz.boot3.common.search.Query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 9:54
 */
@Getter
@Setter
public class QueryCondition implements Serializable {
    @Serial
    private static final long serialVersionUID = -6396284238731107291L;

    private EsKeyword esKeyword;

    private String colum;

    private Object val;

    private List<QueryCondition> children;

    public QueryCondition(EsKeyword esKeyword) {
        this.esKeyword = esKeyword;
        this.children = new ArrayList<>();
    }

    public QueryCondition(EsKeyword esKeyword, String colum, Object val) {
        this.esKeyword = esKeyword;
        this.colum = colum;
        this.val = val;
        this.children = new ArrayList<>();
    }
}
