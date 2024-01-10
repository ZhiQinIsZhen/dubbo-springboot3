package com.liyz.boot3.common.search.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.exception.RemoteServiceException;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/4 14:01
 */
public class SearchException extends RemoteServiceException {
    @Serial
    private static final long serialVersionUID = 8746746093948718766L;

    public SearchException() {
        super();
    }

    public SearchException(IExceptionService codeService) {
        super(codeService);
    }

    public SearchException(String code, String message) {
        super(code, message);
    }
}
