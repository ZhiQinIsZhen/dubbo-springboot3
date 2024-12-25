package com.liyz.boot3.security.client.constant;

import org.springframework.http.HttpHeaders;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 11:17
 */
public interface SecurityClientConstant {

    /**
     * 免登录资源
     */
    String[] KNIFE4J_IGNORE_RESOURCES = new String[] {
            "/liyz/error",
            "/doc.html",
            "/favicon.ico",
            "/webjars/**",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources"
    };

    /**
     * 监控资源
     */
    String[] ACTUATOR_RESOURCES = new String[] {"/actuator/**"};

    String DUBBO_AUTH_GROUP = "auth";

    String OPTIONS_PATTERNS = "/**";

    String DEFAULT_TOKEN_HEADER_KEY = HttpHeaders.AUTHORIZATION;

    String DUBBO_APPLICATION_NAME_PROPERTY = "dubbo.application.name";

    String AUTH_SERVICE_BEAN_NAME = "remoteAuthService-auth";

    String JWT_SERVICE_BEAN_NAME = "remoteJwtParseService-auth";

    String AUTH_MANAGER_BEAN_NAME = "authenticationManager";

    /**
     * 默认cookie过期时间
     */
    int DEFAULT_COOKIE_EXPIRY = 30 * 60;
}
