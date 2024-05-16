package com.liyz.boot3.api.user.config;

import com.liyz.boot3.common.api.config.SwaggerConfig;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:38
 */
@Configuration
public class ApiSwaggerConfig extends SwaggerConfig {

    @Bean
    public GroupedOpenApi allGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("全部")
                .packagesToScan("com.liyz.boot3.api.user.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi authGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("鉴权")
                .packagesToScan("com.liyz.boot3.api.user.controller.authen")
                .build();
    }

    @Bean
    public GroupedOpenApi searchGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("搜索")
                .packagesToScan("com.liyz.boot3.api.user.controller.search")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("用户")
                .packagesToScan("com.liyz.boot3.api.user.controller.user")
                .build();
    }

    @Bean
    public GroupedOpenApi testGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("测试")
                .packagesToScan("com.liyz.boot3.api.user.controller.test")
                .build();
    }
}
