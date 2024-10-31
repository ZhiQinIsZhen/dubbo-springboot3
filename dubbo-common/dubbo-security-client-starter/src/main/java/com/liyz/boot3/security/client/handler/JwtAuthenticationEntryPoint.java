package com.liyz.boot3.security.client.handler;

import com.google.common.base.Charsets;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.Objects;

/**
 * Desc:auth point
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:27
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(Charsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(Objects.requireNonNull(JsonMapperUtil.toJSONString(Result.error(AuthExceptionCodeEnum.AUTHORIZATION_FAIL))));
    }
}
