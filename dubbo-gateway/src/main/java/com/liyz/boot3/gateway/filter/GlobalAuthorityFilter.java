package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import com.liyz.boot3.gateway.properties.AuthorityMappingProperties;
import com.liyz.boot3.gateway.util.ResponseUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.remote.RemoteAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * Desc:权限全局过滤器
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/27 16:44
 */
@Slf4j
@Component
@RefreshScope
@EnableConfigurationProperties(AuthorityMappingProperties.class)
public class GlobalAuthorityFilter implements GlobalFilter, GatewayConstant, Ordered {

    private final AuthorityMappingProperties properties;

    public GlobalAuthorityFilter(AuthorityMappingProperties properties) {
        this.properties = properties;
    }

    @DubboReference(group = "auth")
    private RemoteAuthService remoteAuthService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return ResponseUtil.response(response, AuthExceptionCodeEnum.NOT_FOUND);
        }
        AuthUserBO authUserBO = exchange.getAttribute(AUTH_INFO);
        if (Objects.isNull(authUserBO)) {
            return chain.filter(exchange);
        }
        String path = request.getURI().getPath();
        String clientId = route.getId();
        if (this.pathMatch(path, properties.getWhite().get(DEFAULT_ANONYMOUS_MAPPING))
                || this.pathMatch(path, properties.getWhite().get(clientId))) {
            return chain.filter(exchange);
        }
        if (CollectionUtils.isEmpty(authUserBO.getAuthorities())) {
            authUserBO.setAuthorities(remoteAuthService.authorities(authUserBO));
        }
        if (!CollectionUtils.isEmpty(authUserBO.getAuthorities())) {
            for (AuthUserBO.AuthGrantedAuthorityBO item : authUserBO.getAuthorities()) {
                if (clientId.equalsIgnoreCase(item.getClientId()) && PatternUtil.pathMatch(path, item.getAuthorityCode())) {
                    return chain.filter(exchange);
                }
            }
        }
        return ResponseUtil.response(response, AuthExceptionCodeEnum.NO_RIGHT);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 30000;
    }
}
