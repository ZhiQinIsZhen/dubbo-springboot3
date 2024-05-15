package com.liyz.boot3.task.xxl.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/15 10:55
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobProperties {

    private String adminAddress;

    private String appName;

    private String ip;

    private String logPath;

    private int logRetentionDays;

    private int port;

    private String accessToken;
}
