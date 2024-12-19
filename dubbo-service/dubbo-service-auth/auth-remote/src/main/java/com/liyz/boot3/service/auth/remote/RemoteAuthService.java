package com.liyz.boot3.service.auth.remote;

import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserLoginBO;
import com.liyz.boot3.service.auth.bo.AuthUserLogoutBO;
import com.liyz.boot3.service.auth.bo.AuthUserRegisterBO;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:00
 */
public interface RemoteAuthService {

    /**
     * 用户注册
     *
     * @param authUserRegister 注册参数
     * @return True：注册成功；false：注册失败
     */
    Boolean registry(@NotNull AuthUserRegisterBO authUserRegister);

    /**
     * 登录
     *
     * @param authUserLogin 登录参数
     * @return 当前登录时间
     */
    AuthUserBO login(@NotNull AuthUserLoginBO authUserLogin);

    /**
     * 登出
     *
     * @param authUserLogout 登出参数
     * @return True：登出成功；false：登出失败
     */
    Boolean logout(@NotNull AuthUserLogoutBO authUserLogout);

    /**
     * 获取权限列表
     *
     * @param authUser 认证用户信息
     * @return 权限列表
     */
    default List<AuthUserBO.AuthGrantedAuthorityBO> authorities(@NotNull AuthUserBO authUser) {
        return new ArrayList<>();
    }
}
