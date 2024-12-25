package com.liyz.boot3.security.client.properties;

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
@ConfigurationProperties("gateway.auth.header")
public class GatewayAuthHeaderProperties {

    /**
     * header key
     */
    private String key;

    /**
     * aes secret
     */
    private String secret;
}
