package com.liyz.boot3.common.search.Query;


import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 14:36
 */
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>> extends Wrapper<T>
        implements Condition<Children, R> {

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;

    private T entity;
    private Class<T> entityClass;
    @Getter
    @Setter
    private List<String> includes = new ArrayList<>();
    @Getter
    private Query query;
    @Getter
    private final List<QuerySort> sorts = new ArrayList<>();
    @Getter
    private final List<QueryAgg> aggs = new ArrayList<>();

    public Class<T> getEntityClass() {
        if (entityClass == null && entity != null) {
            entityClass = (Class<T>) entity.getClass();
        }
        return entityClass;
    }

    public Children setEntityClass(Class<T> entityClass) {
        if (entityClass != null) {
            this.entityClass = entityClass;
        }
        return typedThis;
    }

    @Override
    public T getEntity() {
        return entity;
    }

    public Children setEntity(T entity) {
        this.entity = entity;
        return typedThis;
    }

    @Override
    public Children term(boolean condition, String column, Object val) {
        return addQuery(condition, EsBoolKey.MUST, getChildQuery(column, val, EsBoolChildKey.TERM));
    }

    @Override
    public Children terms(boolean condition, String column, List<Object> vals) {
        return addQuery(condition, EsBoolKey.MUST, getChildQuery(column, vals, EsBoolChildKey.TERMS));
    }

    protected Children addQuery(boolean condition, EsBoolKey esBoolKey, Query childQuery) {
        if (condition) {
            boolean needInit = query == null;
            esBoolKey = esBoolKey == null ? EsBoolKey.MUST : esBoolKey;
            switch (esBoolKey) {
                case FILTER -> {
                    if (needInit) {
                        query = QueryBuilders.bool(bq -> bq.filter(childQuery));
                    } else {
                        query.bool().filter().add(childQuery);
                    }
                }
                case MUST -> {
                    if (needInit) {
                        query = QueryBuilders.bool(bq -> bq.must(childQuery));
                    } else {
                        query.bool().must().add(childQuery);
                    }
                }
                case SHOULD -> {
                    if (needInit) {
                        query = QueryBuilders.bool(bq -> bq.should(childQuery));
                    } else {
                        query.bool().should().add(childQuery);
                    }
                }
                case NOT_MUST -> {
                    if (needInit) {
                        query = QueryBuilders.bool(bq -> bq.mustNot(childQuery));
                    } else {
                        query.bool().mustNot().add(childQuery);
                    }
                }
            }
        }
        return typedThis;
    }

    protected Query getChildQuery(String column, Object val, EsBoolChildKey esBoolChildKey) {
        Query result = null;
        switch (esBoolChildKey) {
            case TERM -> result = Query.of(q -> q.term(tm -> tm.field(column).value(getFieldValue(val))));
            case TERMS -> result = Query.of(q -> q.terms(tms -> tms.field(column).terms(tqf -> tqf.value(getFieldValues(val)))));
        }
        return result;
    }

    @Override
    public Children sort(boolean condition, String column, SortOrder esSort) {
        addSort(condition, column, esSort);
        return typedThis;
    }

    private void addSort(boolean condition, String column, SortOrder esSort) {
        if (condition) {
            sorts.add(new QuerySort(esSort, column));
        }
    }

    public Children agg(R column, Aggregation.Kind kind) {
        return agg(column, kind, EsSortField.KEY, SortOrder.Desc, 1, 1000);
    }

    public Children agg(R column, Aggregation.Kind kind, EsSortField esSortField, SortOrder esSort) {
        return agg(column, kind, esSortField, esSort, 1, 1000);
    }

    public Children agg(R column, Aggregation.Kind kind, EsSortField esSortField, SortOrder esSort, int minDocCount, int maxCount) {
        aggs.add(new QueryAgg(columnToString(column), kind, esSortField, esSort, minDocCount, maxCount));
        return typedThis;
    }
    public Children setQuery(Query query) {
        this.query = query;
        return typedThis;
    }

    protected FieldValue getFieldValue(Object value) {
        if (value == null) {
            return FieldValue.NULL;
        }
        if (value instanceof String strValue) {
            return FieldValue.of(strValue);
        }
        if (value instanceof Long longValue) {
            return FieldValue.of(longValue);
        }
        if (value instanceof Double doubleValue) {
            return FieldValue.of(doubleValue);
        }
        if (value instanceof Boolean boolValue) {
            return FieldValue.of(boolValue);
        }
        return FieldValue.of(value.toString());
    }

    protected List<FieldValue> getFieldValues(Object value) {
        if (value == null) {
            return List.of(FieldValue.NULL);
        }
        if (value instanceof String strValue) {
            return List.of(FieldValue.of(strValue));
        }
        if (value instanceof Long longValue) {
            return List.of(FieldValue.of(longValue));
        }
        if (value instanceof Double doubleValue) {
            return List.of(FieldValue.of(doubleValue));
        }
        if (value instanceof Boolean boolValue) {
            return List.of(FieldValue.of(boolValue));
        }
        if (value instanceof List<?> valList) {
            return valList.stream().map(this::getFieldValue).collect(Collectors.toList());
        }
        return List.of(FieldValue.of(value.toString()));
    }
}
