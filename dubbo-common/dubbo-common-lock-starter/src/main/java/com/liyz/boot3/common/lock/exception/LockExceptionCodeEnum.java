package com.liyz.boot3.common.lock.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/17 11:08
 */
public enum LockExceptionCodeEnum implements IExceptionService {
    NOT_HELD_LOCK("14001", "未持有锁"),
    ;

    private final String code;

    private final String message;

    LockExceptionCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getName() {
        return name();
    }
}
