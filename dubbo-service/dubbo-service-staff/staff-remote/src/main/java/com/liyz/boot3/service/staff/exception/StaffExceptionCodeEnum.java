package com.liyz.boot3.service.staff.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 17:15
 */
@AllArgsConstructor
public enum StaffExceptionCodeEnum implements IExceptionService {
    ;

    private final String code;

    private final String message;

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
