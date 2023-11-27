package com.liyz.boot3.common.remote.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:35
 */
@Getter
@Setter
public class RemoteServiceException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private IExceptionService codeService;

    public RemoteServiceException() {
        this(CommonExceptionCodeEnum.FAIL);
    }

    public RemoteServiceException(IExceptionService codeService) {
        super(codeService.getMessage());
        this.codeService = codeService;
    }

    public String getCode() {
        return codeService.getCode();
    }

    @Override
    public String getMessage() {
        return codeService.getMessage();
    }
}
