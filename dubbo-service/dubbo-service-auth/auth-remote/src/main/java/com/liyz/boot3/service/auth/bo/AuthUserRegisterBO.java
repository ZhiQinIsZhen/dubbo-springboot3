package com.liyz.boot3.service.auth.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 9:50
 */
@Getter
@Setter
public class AuthUserRegisterBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户真实名称
     */
    private String realName;

    /**
     * 客户端ID
     */
    private String clientId;
}
