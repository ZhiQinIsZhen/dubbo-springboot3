package com.liyz.boot3.api.test.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiYangzhen
 * @version : RunRiskIndexProperties.java, v 0.1 2024-11-04 11:48 LiYangzhen Exp $
 */
@Data
@Component
@ConfigurationProperties(prefix = "run-risk.controller")
public class RunRiskIndexProperties {

    private Map<String, Integer> index = new HashMap<>();
}
