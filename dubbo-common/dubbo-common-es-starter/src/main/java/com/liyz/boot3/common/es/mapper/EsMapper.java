package com.liyz.boot3.common.es.mapper;

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


    T selectById(Serializable id);

    List<T> selectBatchIds(Collection<? extends Serializable> idList);
}
