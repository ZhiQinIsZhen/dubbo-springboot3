package com.liyz.boot3.security.client.context;

import com.liyz.boot3.service.auth.bo.AuthUserBO;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 10:39
 */
public class AuthContext {

    private static final InheritableThreadLocal<AuthUserBO> innerContext = new InheritableThreadLocal<>();

    /**
     * 获取认证用户
     *
     * @return 认证用户
     */
    public static AuthUserBO getAuthUser() {
        AuthUserBO authUserBO = innerContext.get();
        if (Objects.isNull(authUserBO)) {
            authUserBO = new AuthUserBO();
            authUserBO.setAuthId(1L);
        }
        return authUserBO;
    }

    /**
     * 设置认证用户
     *
     * @param authUser 认证用户
     */
    public static void setAuthUser(AuthUserBO authUser) {
        innerContext.set(authUser);
    }

    /**
     * 移除认证用户
     */
    public static void remove() {
        innerContext.remove();
    }
}
