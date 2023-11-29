package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.gateway.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/28 19:58
 */
@Slf4j
@Component
public class GlobalLimitFilterGatewayFilterFactory extends RequestRateLimiterGatewayFilterFactory {

    public GlobalLimitFilterGatewayFilterFactory(RateLimiter defaultRateLimiter, KeyResolver defaultKeyResolver) {
        super(defaultRateLimiter, defaultKeyResolver);
    }

    @Override
    public GatewayFilter apply(Config config) {
        KeyResolver resolver = getDefaultKeyResolver();
        RateLimiter<Object> limiter = getDefaultRateLimiter();
        return (exchange, chain) -> resolver.resolve(exchange).flatMap(key -> {
            String routeId = config.getRouteId();
            if (routeId == null) {
                Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                routeId = route.getId();
            }
            String finalRouteId = routeId;
            return limiter.isAllowed(routeId, key).flatMap((response) -> {
                for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
                    exchange.getResponse().getHeaders().add(header.getKey(), header.getValue());
                }
                if (response.isAllowed()) {
                    return chain.filter(exchange);
                }
                log.warn("已限流: {}, key : {}", finalRouteId, key);
                return ResponseUtil.response(exchange.getResponse(), CommonExceptionCodeEnum.OUT_LIMIT_COUNT);
            });
        });
    }
}
