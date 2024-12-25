package com.liyz.boot3.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/28 19:20
 */
@Getter
@Setter
@RefreshScope
@ConfigurationProperties(prefix = "authority.mapping")
public class AuthorityMappingProperties {

    private Map<String, Set<String>> white = new HashMap<>();
}
