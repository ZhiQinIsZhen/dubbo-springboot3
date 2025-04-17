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
public class UserInfoBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 477985923412638468L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户真实姓名
     */
    private String realName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 安全盐值
     */
    private String salt;

    /**
     * 注册时间
     */
    private Date registryTime;
}
