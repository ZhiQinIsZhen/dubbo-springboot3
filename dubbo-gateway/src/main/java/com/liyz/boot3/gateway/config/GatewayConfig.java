package com.liyz.boot3.gateway.config;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/29 9:33
 */
@Slf4j
@RefreshScope
@Configuration
public class GatewayConfig {

    @Value(value = "${test}")
    private String test;

    @RefreshScope
    @Primary
    @Bean("keyResolver")
    public KeyResolver keyResolver() {
        return exchange -> {
            log.info("test:{}", test);
            String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            String path = exchange.getRequest().getURI().getPath();
            log.info("ip : {}, path : {}", ip, path);
            return Mono.just(Joiner.on("_").join(ip, path));
        };
    }
}
