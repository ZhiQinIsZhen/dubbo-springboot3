package com.liyz.boot3.common.dao.transaction.impl;

import com.liyz.boot3.common.dao.transaction.LocalTransactionTemplate;
import com.liyz.boot3.common.dao.transaction.callback.LocalTransactionCallback;
import com.liyz.boot3.common.dao.transaction.callback.LocalTransactionCallbackWithoutResult;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Desc:本地事务模板，需要使用本地事务的，调用execute方法
 * 注：不要在execute内部的action中try掉异常，否则事务将不会rollback
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024-11-15 17:25
 */
@Slf4j
@Getter
@Setter
public class LocalTransactionTemplateImpl implements LocalTransactionTemplate {

    private TransactionTemplate transactionTemplate;

    @Override
    public <T> T execute(LocalTransactionCallback<T> action) {
        long start = System.currentTimeMillis();
        try {
            return transactionTemplate.execute(action);
        } catch (RemoteServiceException rse) {
            throw rse;
        } catch (Throwable e) {
            log.error("transaction name : {} --has throwable", action.getLocalTransactionName(), e);
            throw new RemoteServiceException(CommonExceptionCodeEnum.DB_TRANSACTION_ERROR);
        } finally {
            long useTime = System.currentTimeMillis() - start;
            log.info("transaction name:{}, useTime(ms):{}", action.getLocalTransactionName(), useTime);
            action.onFinally();
        }
    }

    @Override
    public void execute(LocalTransactionCallbackWithoutResult action) {
        long start = System.currentTimeMillis();
        try {
            transactionTemplate.execute(action);
        } catch (RemoteServiceException rse) {
            throw rse;
        } catch (Throwable e) {
            log.error("transaction name : {} --has throwable", action.getLocalTransactionName(), e);
            throw new RemoteServiceException(CommonExceptionCodeEnum.DB_TRANSACTION_ERROR);
        } finally {
            long useTime = System.currentTimeMillis() - start;
            log.info("transaction name:{}, useTime(ms):{}", action.getLocalTransactionName(), useTime);
            action.onFinally();
        }
    }
}
