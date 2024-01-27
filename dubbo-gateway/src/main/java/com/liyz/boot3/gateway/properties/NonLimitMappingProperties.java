package com.liyz.boot3.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.Set;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/26 13:25
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "non.limit.mapping")
public class NonLimitMappingProperties {

    private Map<String, Set<String>> server;
}
