package com.liyz.boot3.service.user.exception;


import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:32
 */
public class RemoteUserServiceException extends RemoteServiceException {
    @Serial
    private static final long serialVersionUID = -5631823158974550846L;

    public RemoteUserServiceException() {
        super();
    }

    public RemoteUserServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
