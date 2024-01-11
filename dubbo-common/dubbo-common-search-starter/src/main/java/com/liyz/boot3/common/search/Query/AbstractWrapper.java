package com.liyz.boot3.common.search.Query;


import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 14:36
 */
public abstract class AbstractWrapper<T, R, Children extends AbstractWrapper<T, R, Children>> extends Wrapper<T> {

    /**
     * 占位符
     */
    protected final Children typedThis = (Children) this;

    private T entity;
    private Class<T> entityClass;
    private QueryCondition queryCondition;
    private List<QuerySort> sorts = new ArrayList<>();
    private List<QueryAgg> aggs = new ArrayList<>();

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

    public QueryCondition getQueryCondition() {
        return queryCondition;
    }

    public List<QuerySort> getSorts() {
        return sorts;
    }

    public List<QueryAgg> getAggs() {
        return aggs;
    }

    /**
     * 等于 =
     *
     * @param column    字段
     * @param val       值
     * @return children
     */
    public Children term(R column, Object val) {
        return term(true, column, val);
    }

    public Children term(boolean condition, R column, Object val) {
        return addCondition(condition, column, EsKeyword.TERM, val);
    }

    /**
     * 等于 in
     *
     * @param column    字段
     * @param vals       值
     * @return children
     */
    public Children terms(R column, List<Object> vals) {
        return terms(true, column, vals);
    }

    public Children terms(boolean condition, R column, List<Object> vals) {
        return addCondition(condition, column, EsKeyword.TERMS, vals);
    }

    protected Children addCondition(boolean condition, R column, EsKeyword esKeyword, Object val) {
        if (condition) {
            if (queryCondition == null) {
                queryCondition = new QueryCondition(EsKeyword.MUST);
            }
            queryCondition.getChildren().add(new QueryCondition(esKeyword, columnToString(column), val));
        }
        return typedThis;
    }

    public Children sort(R column, EsSort esSort) {
        return sort(true, column, esSort);
    }

    public Children sort(boolean condition, R column, EsSort esSort) {
        return addSort(condition, column, esSort);
    }

    protected Children addSort(boolean condition, R column, EsSort esSort) {
        if (condition) {
            sorts.add(new QuerySort(esSort, columnToString(column)));
        }
        return typedThis;
    }

    /**
     * 获取 columnName
     */
    protected abstract String columnToString(R column);

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
}
