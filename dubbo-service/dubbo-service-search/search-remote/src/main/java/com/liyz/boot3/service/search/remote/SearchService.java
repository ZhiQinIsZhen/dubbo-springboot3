package com.liyz.boot3.service.search.remote;

import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.search.bo.BaseBO;
import com.liyz.boot3.service.search.bo.agg.AggBO;
import com.liyz.boot3.service.search.query.PageQuery;
import com.liyz.boot3.service.search.query.agg.AggQuery;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/8/17 15:22
 */
@Deprecated
public interface SearchService<BO extends BaseBO, BaseQuery extends PageQuery> {

    /**
     * 根据主键查询
     *
     * @param id 主键
     * @return 结果
     */
    BO getById(String id);

    /**
     * 根据主键列表查询
     *
     * @param ids 主键列表
     * @return 结果
     */
    List<BO> getByIds(List<String> ids);

    /**
     * 查询单条数据
     *
     * @param baseQuery 查询条件
     * @return 结果
     */
    BO search(BaseQuery baseQuery);

    /**
     * 查询列表数据
     *
     * @param baseQuery 查询条件
     * @return 结果
     */
    List<BO> searchList(BaseQuery baseQuery);

    /**
     * 分页查询数据
     *
     * @param baseQuery 查询条件
     * @return 结果
     */
    RemotePage<BO> searchPage(BaseQuery baseQuery);

    /**
     * 聚合查询
     *
     * @param baseQuery 查询条件
     * @param aggQuery 聚合条件
     * @return 聚合结果
     */
    List<AggBO> agg(BaseQuery baseQuery, AggQuery aggQuery);
}
