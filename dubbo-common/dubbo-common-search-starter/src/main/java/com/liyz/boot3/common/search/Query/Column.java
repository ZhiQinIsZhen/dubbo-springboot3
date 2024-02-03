package com.liyz.boot3.common.search.Query;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/3 14:17
 */
public interface Column<R> {

    /**
     * 获取字段属性
     *
     * @param column 字段
     * @return 字段属性
     */
    String columnToString(R column);
}
