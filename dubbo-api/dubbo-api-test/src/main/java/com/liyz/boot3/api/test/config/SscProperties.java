package com.liyz.boot3.api.test.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/7 9:58
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "ssc")
public class SscProperties {

    private String baseUrl;

    private String authHeader;

    private String authValue;
}
