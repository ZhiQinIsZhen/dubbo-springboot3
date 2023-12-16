package com.liyz.boot3.common.api.result;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.liyz.boot3.common.api.util.I18nMessageUtil;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.IExceptionService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Desc:restful response body
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:17
 */
@Getter
@Setter
@JsonPropertyOrder({"code", "message", "data"})
public class Result<T> {

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(T data) {
        this(CommonExceptionCodeEnum.SUCCESS);
        this.data = data;
    }

    public Result(IExceptionService codeEnum) {
        this(codeEnum.getCode(), I18nMessageUtil.getMessage(codeEnum.getName(), codeEnum.getMessage(), null));
    }

    @Schema(description = "code码")
    private String code;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "数据体")
    private T data;

    public static <E> Result<E> success(E data) {
        return new Result<>(data);
    }

    public static <E> Result<E> success() {
        return success(null);
    }

    public static <E> Result<E> error(IExceptionService codeEnum) {
        return new Result<>(codeEnum);
    }

    public static <E> Result<E> error(String code, String message) {
        return new Result<>(code, message);
    }
}
