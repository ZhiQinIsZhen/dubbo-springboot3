package com.liyz.boot3.security.client.user.impl;

import com.google.common.base.Joiner;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import com.liyz.boot3.exception.util.LoginUserContext;
import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.security.client.user.AuthUserDetails;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.enums.Device;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
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
public class UserDetailsServiceImpl implements UserDetailsService, EnvironmentAware {

    private static String clientId;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        int index = username.indexOf(CommonServiceConstant.DEFAULT_JOINER);
        if (index == -1) {
            username = Joiner.on(CommonServiceConstant.DEFAULT_JOINER).join(Device.WEB.getType(), clientId, username);
            index = username.indexOf(CommonServiceConstant.DEFAULT_JOINER);
        }
        AuthUserBO authUserBO = AuthContext.AuthService.loadByUsername(username.substring(index + 1),
                Device.getByType(Integer.parseInt(username.substring(0, index))));
        if (Objects.isNull(authUserBO)) {
            throw new UsernameNotFoundException(AuthExceptionCodeEnum.AUTHORIZATION_FAIL.getMessage());
        }
        RpcContext.getClientAttachment().setAttachment(LoginUserContext.ATTACHMENT_LOGIN_USER, authUserBO.getAuthId().toString());
        return AuthUserDetails.build(authUserBO);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.clientId = environment.getProperty(SecurityClientConstant.DUBBO_APPLICATION_NAME_PROPERTY);
    }
}
