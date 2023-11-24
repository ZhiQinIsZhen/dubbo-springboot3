package com.liyz.boot3.service.staff.service;

import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.staff.model.base.StaffAuthBaseDO;
import org.springframework.beans.factory.InitializingBean;

import java.util.EnumMap;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/14 9:19
 */
public interface LoginTypeService extends InitializingBean {

    //容器
    Map<LoginType, LoginTypeService> LOGIN_TYPE_MAP = new EnumMap<>(LoginType.class);

    /**
     * init
     *
     * @throws Exception 异常
     */
    @Override
    default void afterPropertiesSet() throws Exception {
        LOGIN_TYPE_MAP.put(loginType(), this);
    }

    /**
     * 获取认证信息
     *
     * @param username 用户名
     * @return 认证信息
     */
    StaffAuthBaseDO getByUsername(String username);

    /**
     * 获取登录方式
     *
     * @return 登录方式
     */
    LoginType loginType();
}
