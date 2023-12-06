package com.liyz.boot3.gateway.config;

import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.gateway.util.ResponseUtil;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/6 16:33
 */
@Configuration
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse resp = exchange.getResponse();
        if (resp.isCommitted()) {
            return Mono.error(ex);
        }
        if (ex instanceof ConnectException) {
            return ResponseUtil.response(resp, CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
        }
        return ResponseUtil.response(resp, CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
    }
}
