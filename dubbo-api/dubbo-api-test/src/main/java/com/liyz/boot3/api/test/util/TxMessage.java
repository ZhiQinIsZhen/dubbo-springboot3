package com.liyz.boot3.api.test.util;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/1 17:33
 */
@Getter
@Setter
public class TxMessage<T,R> implements Serializable {
    @Serial
    private static final long serialVersionUID = -5731993399979952927L;

    // 消息体
    private T data;
    // 业务操作
    private Callable<R> callable;
    //消息关键字，用于消息查询
    private String key;
    // 消息返回结果
    private R result;

    public TxMessage(String key, T data, Callable<R> callable) {
        this.data = data;
        this.callable = callable;
        this.key = key;
    }
}
