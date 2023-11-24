package com.liyz.boot3.service.auth.exception;

import com.liyz.boot3.common.remote.exception.IExceptionService;
import lombok.AllArgsConstructor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 9:25
 */
@AllArgsConstructor
public enum AuthExceptionCodeEnum implements IExceptionService {
    FORBIDDEN("401", "登录后进行操作"),
    NO_RIGHT("403", "暂无权限"),
    LOGIN_FAIL("20001", "用户名或者密码错误"),
    AUTHORIZATION_FAIL("20002", "认证失败"),
    AUTHORIZATION_TIMEOUT("20003", "认证过期"),
    REGISTRY_ERROR("20004", "注册错误"),
    LACK_SOURCE_ID("20005", "注册错误: 缺少资源客户端ID"),
    NON_SET_SOURCE_ID("20006", "注册错误: 资源服务未配置该资源客户端ID"),
    LOGIN_ERROR("20007", "登录错误"),
    OTHERS_LOGIN("20008", "该账号已在其他地方登录"),
    MOBILE_EXIST("20009", "该手机号码已注册"),
    EMAIL_EXIST("20010", "该邮箱地址已注册"),
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
