package com.liyz.boot3.gateway.filter;

import com.liyz.boot3.common.remote.exception.RemoteServiceException;
import com.liyz.boot3.gateway.constant.GatewayConstant;
import com.liyz.boot3.gateway.properties.AnonymousMappingProperties;
import com.liyz.boot3.gateway.util.ResponseUtil;
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
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
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
        this.cacheBodyLog(exchange);
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        if (route == null) {
            return ResponseUtil.response(response, AuthExceptionCodeEnum.NOT_FOUND);
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
            return ResponseUtil.response(response, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        try {
            AuthUserBO authUserBO = remoteJwtParseService.parseToken(jwt, clientId);
            if (Objects.isNull(authUserBO)) {
                return ResponseUtil.response(response, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
            }
            exchange.getAttributes().put(GatewayConstant.AUTH_INFO, authUserBO);
        } catch (RemoteServiceException e) {
            return ResponseUtil.response(response, e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("parse token error", e);
            return ResponseUtil.response(response, AuthExceptionCodeEnum.AUTHORIZATION_FAIL);
        }
        return chain.filter(exchange);
    }

    /**
     * 打印缓存的body日志
     *
     * @param exchange
     */
    private void cacheBodyLog(ServerWebExchange exchange) {
        Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        if (Objects.isNull(cachedBody)) {
            return;
        }
        if (cachedBody instanceof DefaultDataBuffer buffer) {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            log.info("cachedBody : {}", new String(bytes, StandardCharsets.UTF_8));
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10000;
    }
}
