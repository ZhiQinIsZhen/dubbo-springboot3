package com.liyz.boot3.service.search.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 9:39
 */
public class RemoteSearchServiceException extends RemoteServiceException {
    @Serial
    private static final long serialVersionUID = 7663963796387208961L;

    public RemoteSearchServiceException() {
        super();
    }

    public RemoteSearchServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
