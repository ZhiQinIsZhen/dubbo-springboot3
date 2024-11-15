package com.liyz.boot3.common.dao.transaction;

import com.liyz.boot3.common.dao.transaction.callback.LocalTransactionCallback;
import com.liyz.boot3.common.dao.transaction.callback.LocalTransactionCallbackWithoutResult;

/**
 * Desc:本地事务模板
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024-11-15 17:15
 */
public interface LocalTransactionTemplate {

    /**
     * 带返回值的本地事务模板执行入口
     *
     * @param action action
     * @return 执行结果
     * @param <T> 执行结果类型
     */
    <T> T execute(LocalTransactionCallback<T> action);

    /**
     * 无返回值的本地事务模板执行入口
     *
     * @param action action
     */
    void execute(LocalTransactionCallbackWithoutResult action);
}
