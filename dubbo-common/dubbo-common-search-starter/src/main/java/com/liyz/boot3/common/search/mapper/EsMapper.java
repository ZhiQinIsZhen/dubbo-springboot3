package com.liyz.boot3.common.search.mapper;

import com.liyz.boot3.common.search.Query.LambdaQueryWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 10:44
 */
public interface EsMapper<T> {

    /**
     * 根据主键ID查询数据
     *
     * @param id 主键ID
     * @return 数据结果
     */
    default T selectById(Serializable id) {
        return selectById(id, null);
    }

    /**
     * 根据主键ID查询数据
     *
     * @param id 主键ID
     * @param queryWrapper 查询字段
     * @return 数据结果
     */
    T selectById(Serializable id, LambdaQueryWrapper<T> queryWrapper);

    /**
     * 根据主键IDs查询数据
     *
     * @param idList 主键IDs
     * @return 数据结果
     */
    default List<T> selectBatchIds(Collection<? extends Serializable> idList) {
        return selectBatchIds(idList, null);
    }

    /**
     * 根据主键IDs查询数据
     *
     * @param idList 主键IDs
     * @param queryWrapper 查询字段
     * @return 数据结果
     */
    List<T> selectBatchIds(Collection<? extends Serializable> idList, LambdaQueryWrapper<T> queryWrapper);
}
