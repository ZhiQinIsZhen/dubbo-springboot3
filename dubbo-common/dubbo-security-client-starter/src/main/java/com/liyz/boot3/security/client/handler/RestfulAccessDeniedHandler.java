package com.liyz.boot3.security.client.handler;

import com.google.common.base.Charsets;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

/**
 * Desc:权限失败handler
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 14:24
 */
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding(String.valueOf(Charsets.UTF_8));
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().println(JsonMapperUtil.toJSONString(Result.error(AuthExceptionCodeEnum.NO_RIGHT)));
        response.getWriter().flush();
    }
}
