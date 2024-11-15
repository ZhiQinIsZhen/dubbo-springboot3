package com.liyz.boot3.common.dao.transaction.callback;

import com.liyz.boot3.common.dao.transaction.service.HandlerService;
import lombok.Getter;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;

/**
 * Desc:不带返回结果
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024-11-15 17:22
 */
@Getter
public abstract class LocalTransactionCallbackWithoutResult extends TransactionCallbackWithoutResult implements HandlerService {

    private final String localTransactionName;

    public LocalTransactionCallbackWithoutResult(String localTransactionName) {
        this.localTransactionName = localTransactionName;
    }
}
