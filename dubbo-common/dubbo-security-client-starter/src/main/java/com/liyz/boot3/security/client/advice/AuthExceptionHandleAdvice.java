package com.liyz.boot3.security.client.advice;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.config.AuthSecurityClientProperties;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 11:15
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
@EnableConfigurationProperties(AuthSecurityClientProperties.class)
@ConditionalOnProperty(prefix = "auth.advice", name = "enable", havingValue = "true", matchIfMissing = true)
public class AuthExceptionHandleAdvice {

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public Result<String> badCredentialsException(Exception exception) {
        return Result.error(AuthExceptionCodeEnum.LOGIN_FAIL);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public Result<String> accessDeniedException(Exception exception) {
        return Result.error(AuthExceptionCodeEnum.NO_RIGHT);
    }
}
