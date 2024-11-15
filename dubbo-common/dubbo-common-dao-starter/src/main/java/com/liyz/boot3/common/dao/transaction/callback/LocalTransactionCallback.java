package com.liyz.boot3.common.dao.transaction.callback;

import com.liyz.boot3.common.dao.transaction.service.HandlerService;
import lombok.Getter;
import org.springframework.transaction.support.TransactionCallback;

/**
 * Desc:带返回结果
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024-11-15 17:19
 */
@Getter
public abstract class LocalTransactionCallback<T> implements TransactionCallback<T>, HandlerService {

    private final String localTransactionName;

    public LocalTransactionCallback(String localTransactionName) {
        this.localTransactionName = localTransactionName;
    }
}
