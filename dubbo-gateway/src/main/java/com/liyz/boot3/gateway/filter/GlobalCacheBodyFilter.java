package com.liyz.boot3.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.CacheRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CACHED_SERVER_HTTP_REQUEST_DECORATOR_ATTR;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/30 10:27
 */
@Slf4j
@Component
public class GlobalCacheBodyFilter extends CacheRequestBodyGatewayFilterFactory implements Ordered {

    static final String CACHED_ORIGINAL_REQUEST_BODY_BACKUP_ATTR = "cachedOriginalRequestBodyBackup";

    @Override
    public GatewayFilter apply(Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                URI requestUri = request.getURI();
                String scheme = requestUri.getScheme();

                // Record only http requests (including https)
                if ((!"http".equals(scheme) && !"https".equals(scheme))) {
                    return chain.filter(exchange);
                }
                if (request.getMethod() != HttpMethod.POST) {
                    return chain.filter(exchange);
                }
                Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
                if (cachedBody != null) {
                    return chain.filter(exchange);
                }
                return ServerWebExchangeUtils.cacheRequestBodyAndRequest(exchange, (serverHttpRequest) -> {
                    final ServerRequest serverRequest = ServerRequest.create(
                            exchange.mutate().request(serverHttpRequest).build(), HandlerStrategies.withDefaults().messageReaders());
                    return serverRequest.bodyToMono((config.getBodyClass())).doOnNext(objectValue -> {
                        Object previousCachedBody = exchange.getAttributes()
                                .put(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR, objectValue);
                        if (previousCachedBody != null) {
                            // store previous cached body
                            exchange.getAttributes().put(CACHED_ORIGINAL_REQUEST_BODY_BACKUP_ATTR, previousCachedBody);
                        }
                    }).then(Mono.defer(() -> {
                        ServerHttpRequest cachedRequest = exchange
                                .getAttribute(CACHED_SERVER_HTTP_REQUEST_DECORATOR_ATTR);
                        Assert.notNull(cachedRequest, "cache request shouldn't be null");
                        exchange.getAttributes().remove(CACHED_SERVER_HTTP_REQUEST_DECORATOR_ATTR);
                        return chain.filter(exchange.mutate().request(cachedRequest).build()).doFinally(s -> {
                            //
                            Object backupCachedBody = exchange.getAttributes()
                                    .get(CACHED_ORIGINAL_REQUEST_BODY_BACKUP_ATTR);
                            if (backupCachedBody instanceof DataBuffer dataBuffer) {
                                DataBufferUtils.release(dataBuffer);
                            }
                        });
                    }));
                });
            }

            @Override
            public String toString() {
                return filterToStringCreator(GlobalCacheBodyFilter.this)
                        .append("Body class", config.getBodyClass()).toString();
            }
        };
    }

    @Override
    public int getOrder() {
        return 30;
    }
}
