package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import com.liyz.boot3.gateway.properties.AnonymousMappingProperties;
import com.liyz.boot3.gateway.util.WebExchangeUtil;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.exception.AuthExceptionCodeEnum;
import com.liyz.boot3.service.auth.remote.RemoteJwtParseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Desc:Jwt全局过滤器
 * <p>
 *     gateway的filter中的顺序是由接口{@link Ordered}来决定的
 * </p>
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/27 16:44
 */
@Slf4j
@Component
@RefreshScope
@EnableConfigurationProperties(AnonymousMappingProperties.class)
public class GlobalJWTFilter implements GlobalFilter, GatewayConstant, Ordered {

    private final AnonymousMappingProperties properties;

    public GlobalJWTFilter(AnonymousMappingProperties properties) {
        this.properties = properties;
    }

    @DubboReference(group = "auth")
    private RemoteJwtParseService remoteJwtParseService;

    /**
     * 确认是否需要认证JWT
     *
     * @param exchange 数据
     * @param chain 链
     * @return 结果
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String body;
        if (StringUtils.isNotBlank(body = WebExchangeUtil.getBody(exchange))) {
            log.info(body);
        }
        ServerHttpRequest request = exchange.getRequest();
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return WebExchangeUtil.response(exchange, AuthExceptionCodeEnum.NOT_FOUND);
        }
        String path = request.getURI().getPath();
        String clientId = route.getId();
        if (this.pathMatch(path, properties.getServer().get(GatewayConstant.DEFAULT_ANONYMOUS_MAPPING))
                || this.pathMatch(path, properties.getServer().get(clientId))) {
            return chain.filter(exchange);
        }
        String jwt = Objects.requireNonNullElse(request.getCookies()
                .getFirst(GatewayConstant.AUTHORIZATION), new HttpCookie(GatewayConstant.AUTHORIZATION, null))
                .getValue();
        if (StringUtils.isBlank(jwt)) {
            jwt = request.getHeaders().getFirst(GatewayConstant.AUTHORIZATION);
        } else {
            jwt = UriUtils.decode(jwt, StandardCharsets.UTF_8);
        }
        if (StringUtils.isBlank(jwt)) {
            return WebExchangeUtil.response(exchange, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        try {
            AuthUserBO authUserBO = remoteJwtParseService.parseToken(jwt, clientId);
            if (Objects.isNull(authUserBO)) {
                return WebExchangeUtil.response(exchange, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
            }
            exchange.getAttributes().put(GatewayConstant.AUTH_INFO, authUserBO);
        } catch (RemoteServiceException e) {
            return WebExchangeUtil.response(exchange, e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("parse token error", e);
            return WebExchangeUtil.response(exchange, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10000;
    }
}
