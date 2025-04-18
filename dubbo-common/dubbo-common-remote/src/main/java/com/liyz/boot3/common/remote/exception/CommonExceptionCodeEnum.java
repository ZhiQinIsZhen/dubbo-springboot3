package com.liyz.boot3.common.remote.exception;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:36
 */
public enum CommonExceptionCodeEnum implements IExceptionService{
    SUCCESS("0", "成功"),
    FAIL("1", "失败"),
    PARAMS_VALIDATED("10000", "参数校验失败"),
    REMOTE_SERVICE_FAIL("10005", "服务异常"),
    OUT_LIMIT_COUNT("10006", "访问频率过快"),
    NON_NO_ARGS_CONSTRUCTOR("10007", "没有无参构造方法"),
    NOT_FIND_CLASS("10008", "找不到指定的class"),
    PARSING_PROPERTY_NAME_ERROR("10009", "解析属性名称错误"),
    REFLECTOR_FAIL("10010", "反射失败"),
    DB_TRANSACTION_ERROR("10011", "DB事务异常"),

    DEC_KEY_LENGTH_ERROR("13001", "DEC加密Key长度不能小于8位"),
    DEC_IV_LENGTH_ERROR("13002", "DEC加密IV长度必须等于8位"),
    AEC_KEY_LENGTH_ERROR("13003", "AEC加密Key长度必须为16位或24位或32位"),
    AEC_IV_LENGTH_ERROR("13004", "AEC加密IV长度必须等于16位"),
    ;

    private final String code;

    private final String message;
    ;

    CommonExceptionCodeEnum(String code, String message) {
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
