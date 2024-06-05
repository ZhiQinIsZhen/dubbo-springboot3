package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.gateway.properties.NonLimitMappingProperties;
import com.liyz.boot3.gateway.util.ResponseUtil;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * Desc:限流过滤器
 * <p>
 *     如果用户登陆，则限流key = authId + _ + mapping，否则限流key = ip + _ + mapping
 * </p>
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/28 19:58
 */
@Slf4j
@Component
@EnableConfigurationProperties(NonLimitMappingProperties.class)
public class GlobalLimitFilter extends RequestRateLimiterGatewayFilterFactory implements Ordered {

    private final NonLimitMappingProperties properties;

    public GlobalLimitFilter(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver, NonLimitMappingProperties properties) {
        super(defaultRateLimiter, defaultKeyResolver);
        this.properties = properties;
    }

    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = getDefaultKeyResolver();
        RateLimiter<Object> limiter = this.getDefaultRateLimiter();
        return (exchange, chain) -> resolver.resolve(exchange).flatMap(key -> {
            String path = exchange.getRequest().getURI().getPath();
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            if (route == null) {
                return ResponseUtil.response(exchange.getResponse(), AuthExceptionCodeEnum.NOT_FOUND);
            }
            String clientId = route.getId();
            Set<String> mappingSet = properties.getServer().get(clientId);
            if (!CollectionUtils.isEmpty(mappingSet) && (mappingSet.contains(path) || PatternUtil.pathMatch(path, mappingSet))) {
                return chain.filter(exchange);
            }
            return limiter.isAllowed(clientId, key).flatMap((response) -> {
                if (response.isAllowed()) {
                    return chain.filter(exchange);
                }
                log.warn("已限流: {}, key : {}", clientId, key);
                return ResponseUtil.response(exchange.getResponse(), CommonExceptionCodeEnum.OUT_LIMIT_COUNT);
            });
        });
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
