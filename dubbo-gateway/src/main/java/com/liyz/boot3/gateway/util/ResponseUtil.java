package com.liyz.boot3.gateway.util;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.util.JsonMapperUtil;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/28 9:31
 */
@UtilityClass
public class ResponseUtil {

    /**
     * 返回消息体
     *
     * @param response httpResponse
     * @param serviceCode 错误码
     * @return 消息体
     */
    public static Mono<Void> response(ServerHttpResponse response, IExceptionService serviceCode) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBuffer dataBuffer = response
                .bufferFactory()
                .wrap(JsonMapperUtil.toJSONString(Result.error(serviceCode)).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Flux.just(dataBuffer));
    }
}
