package com.liyz.boot3.security.client.aspect;

import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.security.client.dto.BaseDTO;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/14 14:02
 */
@Order(-1)
@Aspect
@Component
public class AuthInfoInjectionAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void aspectMethod() {
    }

    @Before("aspectMethod()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (Objects.isNull(args) || args.length == 0) {
            return;
        }
        AuthUserBO authUser = Optional.ofNullable(AuthContext.getAuthUser()).orElse(new AuthUserBO());;
        for (Object arg : args) {
            if (arg instanceof BaseDTO baseDTO) {
                baseDTO.setCurrentAuthId(authUser.getAuthId());
                baseDTO.setCurrentLoginType(authUser.getLoginType());
                baseDTO.setCurrentDevice(authUser.getDevice());
            }
        }
    }
}
