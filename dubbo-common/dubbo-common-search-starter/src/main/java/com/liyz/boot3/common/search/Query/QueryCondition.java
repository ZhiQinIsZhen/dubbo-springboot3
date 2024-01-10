package com.liyz.boot3.common.search.Query;

import co.elastic.clients.elasticsearch._types.FieldValue;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<FieldValue> valToList() {
        if (val == null) {
            return null;
        }
        if (val instanceof List<?> valList) {
            return valList.stream().map(item -> FieldValue.of(item.toString())).collect(Collectors.toList());
        }
        List<FieldValue> list = new ArrayList<>();
        list.add(FieldValue.of(val.toString()));
        return list;
    }
}
