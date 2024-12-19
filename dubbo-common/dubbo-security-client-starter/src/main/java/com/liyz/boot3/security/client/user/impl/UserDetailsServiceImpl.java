package com.liyz.boot3.security.client.user.impl;

import com.liyz.boot3.common.api.util.HttpServletContext;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.exception.util.LoginUserContext;
import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.security.client.context.DeviceContext;
import com.liyz.boot3.security.client.user.AuthUserDetails;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserLoginBO;
import com.liyz.boot3.service.auth.enums.LoginType;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 15:45
 */
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, EnvironmentAware {

    private static String clientId;

    private final RemoteAuthService remoteAuthService;

    public UserDetailsServiceImpl(RemoteAuthService remoteAuthService) {
        this.remoteAuthService = remoteAuthService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserLoginBO authUserLoginBO = AuthUserLoginBO
                .builder()
                .username(username)
                .clientId(clientId)
                .loginType(LoginType.getByType(PatternUtil.checkMobileEmail(username)))
                .device(DeviceContext.getDevice(HttpServletContext.getRequest()))
                .ip(HttpServletContext.getIpAddress())
                .build();
        AuthUserBO authUserBO = remoteAuthService.login(authUserLoginBO);
        if (Objects.isNull(authUserBO)) {
            throw new UsernameNotFoundException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL.getMessage());
        }
        Pair<String, String> pair = AuthContext.JwtService.generateToken(authUserBO);
        authUserBO.setJwtPrefix(pair.getLeft());
        authUserBO.setToken(pair.getRight());
        RpcContext.getClientAttachment().setAttachment(LoginUserContext.ATTACHMENT_LOGIN_USER, authUserBO.getAuthId().toString());
        return AuthUserDetails.build(authUserBO);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.clientId = environment.getProperty(SecurityClientConstant.DUBBO_APPLICATION_NAME_PROPERTY);
    }
}
