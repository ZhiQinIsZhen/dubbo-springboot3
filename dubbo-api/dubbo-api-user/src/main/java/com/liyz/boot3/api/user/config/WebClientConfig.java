package com.liyz.boot3.api.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liyz.boot3.api.user.controller.test.SschaService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/20 17:20
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(ObjectMapper objectMapper) {
        return WebClient.builder()
                .baseUrl("https://openapi.sscha.com")
                .defaultHeaders(httpHeaders -> {
                    httpHeaders.add("Authorization", "736fe7a1-6bc4-4663-b1f3-fc99aa1af2b0");
                })
                .build();
    }

    @SneakyThrows
    @Bean
    public SschaService sschaService(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();
        return httpServiceProxyFactory.createClient(SschaService.class);
    }
}
