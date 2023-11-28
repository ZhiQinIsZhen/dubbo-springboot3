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
 * @date 2023/11/28 19:20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "anonymous.mapping")
public class AnonymousMappingProperties {

    private Map<String, Set<String>> server;
}
