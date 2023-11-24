package com.liyz.boot3.service.staff.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 17:16
 */
public class RemoteStaffServiceException extends RemoteServiceException {
    @Serial
    private static final long serialVersionUID = 1309235357509826011L;

    public RemoteStaffServiceException() {
        super();
    }

    public RemoteStaffServiceException(IExceptionService codeService) {
        super(codeService);
    }
}
