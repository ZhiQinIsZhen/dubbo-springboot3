package com.liyz.boot3.gateway.config;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.gateway.util.WebExchangeUtil;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.annotation.Configuration;
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
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        if (ex instanceof ConnectException) {
            return WebExchangeUtil.response(exchange, CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
        } else if (ex instanceof BlockException) {
            return WebExchangeUtil.response(exchange, CommonExceptionCodeEnum.OUT_LIMIT_COUNT);
        }
        return WebExchangeUtil.response(exchange, CommonExceptionCodeEnum.REMOTE_SERVICE_FAIL);
    }


}
