package com.liyz.boot3.common.remote.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:35
 */
@Getter
public class RemoteServiceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RemoteServiceException() {
        this(CommonExceptionCodeEnum.FAIL);
    }

    public RemoteServiceException(IExceptionService codeService) {
        super(codeService.message());
        this.code = codeService.code();
    }

    /**
     * 异常code
     */
    private final String code;
}
