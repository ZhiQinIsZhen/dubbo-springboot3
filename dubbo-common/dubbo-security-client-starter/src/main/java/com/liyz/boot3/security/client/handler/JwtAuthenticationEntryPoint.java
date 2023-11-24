package com.liyz.boot3.security.client.handler;

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
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(JsonMapperUtil.toJSONString(Result.error(AuthExceptionCodeEnum.AUTHORIZATION_FAIL)));
    }
}
