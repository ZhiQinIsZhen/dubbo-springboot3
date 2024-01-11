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
    NOT_EXIST_MAPPER("15003", "未继承EsMapper"),
    NOT_INDEX_NAME("15004", "没有明确的Index名称"),
    NOT_SUPPORT_AGG_TYPE("15005", "不支持的聚合查询"),
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
