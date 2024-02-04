package com.liyz.boot3.common.search.mapper;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.search.query.LambdaQueryWrapper;
import com.liyz.boot3.common.search.response.AggResponse;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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
        return selectById(id, (LambdaQueryWrapper<T>) null);
    }

    /**
     * 根据主键ID查询数据
     *
     * @param id 主键ID
     * @param fn 函数
     * @return 数据结果
     */
    default T selectById(Serializable id, Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> fn) {
        return selectById(id, fn.apply(new LambdaQueryWrapper<>()));
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
        return selectBatchIds(idList, (LambdaQueryWrapper<T>) null);
    }

    /**
     * 根据主键IDs查询数据
     *
     * @param idList 主键IDs
     * @param fn 函数
     * @return 数据结果
     */
    default List<T> selectBatchIds(Collection<? extends Serializable> idList, Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> fn) {
        return selectBatchIds(idList, fn.apply(new LambdaQueryWrapper<>()));
    }

    /**
     * 根据主键IDs查询数据
     *
     * @param idList 主键IDs
     * @param queryWrapper 查询字段
     * @return 数据结果
     */
    List<T> selectBatchIds(Collection<? extends Serializable> idList, LambdaQueryWrapper<T> queryWrapper);

    /**
     * 查询一条记录
     *
     * @param fn 函数
     * @return 数据结果
     */
    default T selectOne(Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> fn) {
        return selectOne(fn.apply(new LambdaQueryWrapper<>()));
    }

    /**
     * 查询一条记录
     *
     * @param queryWrapper 查询字段
     * @return 数据结果
     */
    T selectOne(LambdaQueryWrapper<T> queryWrapper);

    /**
     * 分页查询
     *
     * @param pageBO 分页信息
     * @param fn 函数
     * @return 数据结果
     */
    default RemotePage<T> selectPage(PageBO pageBO, Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> fn) {
        return selectPage(pageBO, fn.apply(new LambdaQueryWrapper<>()));
    }

    /**
     * 分页查询
     *
     * @param pageBO 分页信息
     * @param queryWrapper 查询字段
     * @return 数据结果
     */
    RemotePage<T> selectPage(PageBO pageBO, LambdaQueryWrapper<T> queryWrapper);

    /**
     * 聚合查询
     *
     * @param fn 函数
     * @return 聚合结果
     */
    default List<AggResponse> agg(Function<LambdaQueryWrapper<T>, LambdaQueryWrapper<T>> fn) {
        return agg(fn.apply(new LambdaQueryWrapper<>()));
    }

    /**
     * 聚合查询
     *
     * @param queryWrapper 查询字段
     * @return 聚合结果
     */
    List<AggResponse> agg(LambdaQueryWrapper<T> queryWrapper);
}
