package com.liyz.boot3.security.client.handler;

import com.liyz.boot3.security.client.annotation.AuthUser;
import com.liyz.boot3.security.client.context.AuthContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Desc:自定义参数解析器
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:23
 */
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 这里只是用注解参考，实际可以根据别的方式来支持
     *
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return AuthContext.getAuthUser();
    }
}
