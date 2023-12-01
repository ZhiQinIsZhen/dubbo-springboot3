package com.liyz.boot3.service.user.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 9:31
 */
@AllArgsConstructor
public enum UserExceptionCodeEnum implements IExceptionService {
    ;

    private final String code;

    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
