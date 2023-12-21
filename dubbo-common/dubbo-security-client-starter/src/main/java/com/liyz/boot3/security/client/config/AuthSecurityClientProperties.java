package com.liyz.boot3.security.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/21 15:35
 */
@Getter
@Setter
@ConfigurationProperties("auth.advice")
public class AuthSecurityClientProperties {

    private boolean enable = true;
}
