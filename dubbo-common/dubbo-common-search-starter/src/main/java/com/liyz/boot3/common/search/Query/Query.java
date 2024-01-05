package com.liyz.boot3.common.search.Query;

import java.util.Arrays;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/3 11:16
 */
public interface Query<Children, T, R> {

    /**
     * 指定查询字段
     *
     * @param columns 字段列表
     * @return children
     */
    @SuppressWarnings("unchecked")
    default Children select(R... columns) {
        return select(true, columns);
    }

    /**
     * 指定查询字段
     *
     * @param condition 执行条件
     * @param columns 字段列表
     * @return children
     */
    @SuppressWarnings("unchecked")
    default Children select(boolean condition, R... columns) {
        return select(condition, Arrays.asList(columns));
    }

    /**
     * 指定查询字段
     *
     * @param columns 字段列表
     * @return children
     */
    default Children select(List<R> columns) {
        return select(true, columns);
    }

    /**
     * 指定查询字段
     *
     * @param condition 执行条件
     * @param columns 字段列表
     * @return children
     */
    Children select(boolean condition, List<R> columns);
}
