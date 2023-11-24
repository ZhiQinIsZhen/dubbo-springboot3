package com.liyz.boot3.service.auth.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 9:26
 */
public class RemoteAuthServiceException extends RemoteServiceException {
    @Serial
    private static final long serialVersionUID = -7199913728327633511L;

    public RemoteAuthServiceException() {
        super();
    }

    public RemoteAuthServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
