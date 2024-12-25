package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.gateway.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/30 10:27
 */
@Slf4j
@Component
public class GlobalRequestTimeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(GatewayConstant.GATEWAY_REQUEST_BEGIN_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginTime = exchange.getAttribute(GatewayConstant.GATEWAY_REQUEST_BEGIN_TIME);
            if (Objects.nonNull(beginTime)) {
                log.info("request url : {}, time : {}ms, params : {}", exchange.getRequest().getURI().getRawPath(),
                        System.currentTimeMillis() - beginTime, exchange.getRequest().getQueryParams());
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 999;
    }
}
