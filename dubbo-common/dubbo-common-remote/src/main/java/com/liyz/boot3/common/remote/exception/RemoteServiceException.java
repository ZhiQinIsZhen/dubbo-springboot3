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

    private String code;

    private String message;

    public RemoteServiceException() {
        this(CommonExceptionCodeEnum.FAIL);
    }

    public RemoteServiceException(IExceptionService codeService) {
        this(codeService.getCode(), codeService.getMessage());
    }

    public RemoteServiceException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
