package com.liyz.boot3.common.search.Query;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 15:13
 */
public abstract class Wrapper<T> {

    /**
     * 实体对象（子类实现）
     *
     * @return 泛型 T
     */
    public abstract T getEntity();
}
