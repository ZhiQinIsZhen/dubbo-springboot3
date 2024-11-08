package com.liyz.boot3.api.test.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/7 9:58
 */
@Slf4j
@RestControllerAdvice
public class LogRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> declaringClass = methodParameter.getDeclaringClass();
        log.info(declaringClass.getName());
        log.info("hasMethodAnnotation : {}", methodParameter.hasMethodAnnotation(PostMapping.class));
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        if (inputMessage.getBody().available() <= 0) {
            return inputMessage;
        }
        List<String> sign = inputMessage.getHeaders().get("sign");
        byte[] readAllBytes = inputMessage.getBody().readAllBytes();
        log.info(new String(readAllBytes, StandardCharsets.UTF_8));
        //做解密或者验签
        InputStream rawInputStream = new ByteArrayInputStream(readAllBytes);
        return new HttpInputMessage() {

            @Override
            public HttpHeaders getHeaders() {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody() throws IOException {
                return rawInputStream;
            }
        };
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (Objects.isNull(body)) {
            log.info(body.toString());
        } else {
            log.info("body is null");
        }
        return body;
    }
}
