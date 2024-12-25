package com.liyz.boot3.gateway.filter;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.event.EnableBodyCachingEvent;
import org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter;
import org.springframework.stereotype.Component;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/30 10:27
 */
@Slf4j
@Component
public class GlobalCacheBodyFilter extends AdaptCachedBodyGlobalFilter {

    @Resource
    private GatewayProperties gatewayProperties;

    @PostConstruct
    public void init() {
        gatewayProperties.getRoutes().forEach(route -> {
            EnableBodyCachingEvent event = new EnableBodyCachingEvent(new Object(), route.getId());
            onApplicationEvent(event);
        });
    }
}
