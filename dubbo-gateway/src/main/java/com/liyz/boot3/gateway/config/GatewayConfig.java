package com.liyz.boot3.gateway.config;

import com.google.common.base.Joiner;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

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
public class GatewayConfig {

    @RefreshScope
    @Primary
    @Bean(GatewayConstant.DEFAULT_KEY_RESOLVER_BEAN_NAME)
    public KeyResolver keyResolver() {
        return exchange -> {
            AuthUserBO authUserBO = exchange.getAttribute(GatewayConstant.AUTH_INFO);
            String ip = Objects.requireNonNull(exchange.getRequest().getRemoteAddress()).getAddress().getHostAddress();
            String path = exchange.getRequest().getURI().getPath();
            return Mono.just(Joiner.on(GatewayConstant.DEFAULT_SEPARATOR).join(Objects.isNull(authUserBO) ? ip : authUserBO.getAuthId().toString(), path));
        };
    }
}
