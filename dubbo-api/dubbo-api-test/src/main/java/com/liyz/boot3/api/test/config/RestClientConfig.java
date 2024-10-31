package com.liyz.boot3.api.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/12 9:54
 */
@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .baseUrl("https://openapi.sscha.com/services")
                .defaultHeader("Authorization", "736fe7a1-6bc4-4663-b1f3-fc99aa1af2b0")
                .build();
    }
}
