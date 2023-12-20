package com.liyz.boot3.service.search.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/19 16:28
 */
@Getter
@Setter
@ConfigurationProperties("spring.elasticsearch.pool")
public class ElasticsearchPoolProperties {

    /**
     * 最大连接数
     */
    private int maxConnTotal = 0;

    /**
     * 最大连接分片数
     */
    private int maxConnPerRoute = 0;

    /**
     * 心跳策略时间
     */
    private long keepAliveStrategy = 30000;

    /**
     * 连接请求超时时间
     */
    private int connectionRequestTimeout = 5000;

    /**
     * 期望继续启用
     */
    private boolean expectContinueEnabled = true;

    /**
     * 重定向
     */
    private boolean redirectsEnabled =false;
}
