package com.liyz.boot3.service.search.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 9:38
 */
@AllArgsConstructor
public enum SearchExceptionCodeEnum implements IExceptionService {
    NOT_SUPPORT_METHOD("50001", "不支持该方法"),
    ES_SEARCH_ERROR("50002", "数据查询错误"),
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
