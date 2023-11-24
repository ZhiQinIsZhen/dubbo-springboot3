package com.liyz.boot3.security.client.user;

import com.google.common.base.Joiner;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 16:24
 */
@Getter
@Setter
public class AuthGrantedAuthority extends AuthUserBO.AuthGrantedAuthorityBO implements GrantedAuthority {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String getAuthority() {
        return Joiner.on(CommonServiceConstant.DEFAULT_JOINER).join(getClientId(), getAuthorityCode()).toUpperCase();
    }
}
