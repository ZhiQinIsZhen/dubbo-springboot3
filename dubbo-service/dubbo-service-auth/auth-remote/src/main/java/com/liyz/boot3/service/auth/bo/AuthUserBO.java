package com.liyz.boot3.service.auth.bo;

import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.enums.LoginType;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 10:41
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUserBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6046110472442516409L;

    /**
     * 用户id
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
     * 加密盐
     */
    private String salt;

    /**
     * 客户端ID
     */
    private String clientId;

    /**
     * token
     */
    private String token;

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
     * 检查时间
     * 用于是否但设备登录的
     */
    private Date checkTime;

    /**
     * 用户角色
     */
    private List<Integer> roleIds;

    /**
     * 权限列表
     */
    private List<AuthGrantedAuthorityBO> authorities = new ArrayList<>();

    @Getter
    @Setter
    public static class AuthGrantedAuthorityBO implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 客户端ID
         */
        private String clientId;

        /**
         * 权限码
         */
        private String authorityCode;
    }
}
