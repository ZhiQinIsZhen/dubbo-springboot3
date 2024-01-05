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
     * @param id
     * @return
     */
    default T selectById(Serializable id) {
        return selectById(id, null);
    }

    T selectById(Serializable id, LambdaQueryWrapper<T> queryWrapper);

    /**
     * 根据主键IDs查询数据
     *
     * @param idList
     * @return
     */
    List<T> selectBatchIds(Collection<? extends Serializable> idList);
}
