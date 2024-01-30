package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.gateway.constant.GatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;

/**
 * Desc:todo 这里是伪代码，因为在前置网关上校验过登陆权限，所以在后置网关上不再需要二次校验，可以把认证信息放入header中
 * 传下去，后置网关只需要解析该值就好，如果确保安全，可以加密该值或者解析token数据与其对比等安全方案
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/30 9:57
 */
@Slf4j
@Component
public class GlobalAuthIdHeaderFilter extends AddRequestHeaderGatewayFilterFactory implements Ordered {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                Long authId = exchange.getAttribute(GatewayConstant.AUTH_ID);
                String value = authId == null ? config.getValue() : authId.toString();
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .headers(httpHeaders -> httpHeaders.add(config.getName(), value)).build();

                return chain.filter(exchange.mutate().request(request).build());
            }

            @Override
            public String toString() {
                return filterToStringCreator(GlobalAuthIdHeaderFilter.this)
                        .append(config.getName(), config.getValue()).toString();
            }
        };
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
