package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.util.CryptoUtil;
import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
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
 * Desc:往后传递header过滤器 -- 这里采用了aes加密方式
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/30 9:57
 */
@Slf4j
@Component
public class GlobalAuthInfoHeaderFilter extends AddRequestHeaderGatewayFilterFactory implements Ordered {

    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                AuthUserBO authUserBO = exchange.getAttribute(GatewayConstant.AUTH_INFO);
                String authInfo = authUserBO == null ? null : CryptoUtil.Symmetric.encryptAES(JsonMapperUtil.toJSONString(authUserBO), config.getValue());
                ServerHttpRequest request = exchange
                        .getRequest()
                        .mutate()
                        .headers(httpHeaders -> httpHeaders.add(config.getName(), authInfo))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }

            @Override
            public String toString() {
                return filterToStringCreator(GlobalAuthInfoHeaderFilter.this)
                        .append(config.getName(), config.getValue()).toString();
            }
        };
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 40000;
    }
}
