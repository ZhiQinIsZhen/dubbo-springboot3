package com.liyz.boot3.common.search.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/17 11:08
 */
public enum SearchExceptionCodeEnum implements IExceptionService {
    SERIALIZED_FAIL("15001", "序列化拷贝对象失败"),
    DEFAULT_INVOKER_FAIL("15002", "EsMapper默认方法调用失败"),
    ;

    private final String code;

    private final String message;

    SearchExceptionCodeEnum(String code, String message) {
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
