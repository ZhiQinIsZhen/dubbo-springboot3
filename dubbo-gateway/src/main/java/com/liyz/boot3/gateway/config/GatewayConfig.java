package com.liyz.boot3.gateway.config;

import com.google.common.base.Joiner;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.ContextRefreshedEvent;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

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
public class GatewayConfig implements ApplicationListener<ContextRefreshedEvent> {

    /*@Resource
    private GlobalLimitFilter globalLimitFilter;

    RedisRateLimiter redisRateLimiter;

    RedissonRateLimiter redissonRateLimiter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("dubbo-api-admin", r -> r
                        .path("/admin/**")
                        .filters(f -> f.filter(globalLimitFilter.apply(c -> c.setKeyResolver(keyResolver()).setRateLimiter(redisRateLimiter))))
                        .uri("http://localhost:7071")
                )
                .build();
    }*/

    @Value(value = "${test}")
    private String test;

    @RefreshScope
    @Primary
    @Bean("keyResolver")
    public KeyResolver keyResolver() {
        return exchange -> {
            Long authId = exchange.getAttribute(GatewayConstant.AUTH_ID);
            String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            String path = exchange.getRequest().getURI().getPath();
            return Mono.just(Joiner.on("_").join(Objects.isNull(authId) ? ip : authId.toString(), path));
        };
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
    }
}
