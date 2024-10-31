package com.liyz.boot3.api.test.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/8/15 20:27
 */
public class TestBlockLimitException extends RemoteServiceException {

    public static Result<Boolean> handlerException(BlockException e) {
        throw new TestBlockLimitException(CommonExceptionCodeEnum.OUT_LIMIT_COUNT);
    }

    @Serial
    private static final long serialVersionUID = -5631823158974550846L;

    public TestBlockLimitException() {
        super();
    }

    public TestBlockLimitException(IExceptionService codeService) {
        super(codeService);
    }
}
