package com.liyz.boot3.service.user.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 10:15
 */
@Getter
@Setter
public class UserLoginLogBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8978119199629210583L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 登录方式：1:手机;2:邮箱
     */
    private Integer loginType;

    /**
     * 设备标识
     */
    private Integer device;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录IP地址
     */
    private String ip;
}
