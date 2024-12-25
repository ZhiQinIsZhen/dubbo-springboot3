package com.liyz.boot3.gateway.util;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.util.JsonMapperUtil;
import lombok.experimental.UtilityClass;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Desc:工具类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/28 9:31
 */
@UtilityClass
public class WebExchangeUtil {

    /**
     * 获取请求body数据
     *
     * @param exchange exchange
     * @return body
     */
    public static String getBody(ServerWebExchange exchange) {
        Object cachedBody = exchange.getAttribute(ServerWebExchangeUtils.CACHED_REQUEST_BODY_ATTR);
        if (Objects.isNull(cachedBody)) {
            return null;
        }
        if (cachedBody instanceof DefaultDataBuffer buffer) {
            byte[] bytes = new byte[buffer.readableByteCount()];
            buffer.read(bytes);
            DataBufferUtils.release(buffer);
            buffer.write(bytes);
            return new String(bytes, StandardCharsets.UTF_8);
        }
        return null;
    }

    /**
     * 返回消息体
     *
     * @param exchange exchange
     * @param serviceCode 错误码
     * @return 消息体
     */
    public static Mono<Void> response(ServerWebExchange exchange, IExceptionService serviceCode) {
        return response(exchange, serviceCode.getCode(), serviceCode.getMessage());
    }

    /**
     * 返回消息体
     *
     * @param exchange exchange
     * @param code error code
     * @param msg error message
     * @return 消息体
     */
    public static Mono<Void> response(ServerWebExchange exchange, String code, String msg) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBuffer dataBuffer = response
                .bufferFactory()
                .wrap(JsonMapperUtil.toJSONString(Result.error(code, msg)).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }
}
