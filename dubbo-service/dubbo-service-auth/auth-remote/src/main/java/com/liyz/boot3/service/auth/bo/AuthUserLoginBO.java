package com.liyz.boot3.service.auth.bo;

import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.enums.LoginType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:02
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserLoginBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long authId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录类型
     * @see com.liyz.boot3.service.auth.enums.LoginType
     */
    private LoginType loginType;

    /**
     * 登录设备
     * @see com.liyz.boot3.service.auth.enums.Device
     */
    private Device device;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * 重定向路劲
     */
    private String redirect;
}
