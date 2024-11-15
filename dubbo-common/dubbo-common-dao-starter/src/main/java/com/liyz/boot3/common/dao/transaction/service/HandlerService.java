package com.liyz.boot3.common.dao.transaction.service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024-11-15 17:18
 */
public interface HandlerService {

    /**
     * 执行成功时的处理逻辑
     */
    default void onSuccess() {
    }

    /**
     * 执行失败时的处理逻辑
     */
    default void onError() {
    }

    /**
     * 执行异常时的处理逻辑
     *
     * @param e Exception
     */
    default void onException(Exception e) {
    }

    /**
     * 执行结束（不论成功、失败还是异常等）必须要处理的一些逻辑
     */
    default void onFinally() {
    }
}
