package com.liyz.boot3.common.search.Query;

import co.elastic.clients.elasticsearch._types.SortOrder;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/3 14:10
 */
public interface Condition<Children, R> extends Column<R> {

    /**
     * term condition
     *
     * @param column 属性
     * @param value 值
     * @return children
     */
    default Children term(R column, Object value) {
        return term(true, columnToString(column), value);
    }

    default Children term(boolean condition, R column, Object value) {
        return term(condition, columnToString(column), value);
    }

    default Children term(String column, Object value) {
        return term(true, column, value);
    }

    /**
     * term condition
     *
     * @param condition 条件
     * @param column 属性
     * @param value 值
     * @return children
     */
    Children term(boolean condition, String column, Object value);

    /**
     * terms
     *
     * @param column 属性
     * @param values 值
     * @return children
     */
    default Children terms(R column, List<Object> values) {
        return terms(true, columnToString(column), values);
    }

    default Children terms(boolean condition, R column, List<Object> values) {
        return terms(condition, columnToString(column), values);
    }

    default Children terms(String column, List<Object> values) {
        return terms(true, column, values);
    }

    /**
     * terms
     *
     * @param condition 条件
     * @param column 属性
     * @param values 值
     * @return children
     */
    Children terms(boolean condition, String column, List<Object> values);

    /**
     * sort
     *
     * @param column 属性
     * @param esSort 排序顺序
     * @return children
     */
    default Children sort(R column, SortOrder esSort) {
        return sort(true, columnToString(column), esSort);
    }

    default Children sort(boolean condition, R column, SortOrder esSort) {
        return sort(condition, columnToString(column), esSort);
    }

    default Children sort(String column, SortOrder esSort) {
        return sort(true, column, esSort);
    }

    /**
     * sort
     *
     * @param condition 条件
     * @param column 属性
     * @param esSort 排序顺序
     * @return children
     */
    Children sort(boolean condition, String column, SortOrder esSort);
}
