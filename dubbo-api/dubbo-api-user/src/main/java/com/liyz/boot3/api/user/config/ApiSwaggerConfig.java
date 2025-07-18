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
                .displayName("全部")
                .group("all")
                .packagesToScan("com.liyz.boot3.api.user.controller")
                .build();
    }

    @Bean
    public GroupedOpenApi authGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .displayName("鉴权")
                .group("authentication")
                .packagesToScan("com.liyz.boot3.api.user.controller.authen")
                .build();
    }

    @Bean
    public GroupedOpenApi searchGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .displayName("搜索")
                .group("search")
                .packagesToScan("com.liyz.boot3.api.user.controller.search")
                .build();
    }

    @Bean
    public GroupedOpenApi userGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .displayName("用户")
                .group("user")
                .packagesToScan("com.liyz.boot3.api.user.controller.user")
                .build();
    }

    @Bean
    public GroupedOpenApi testGroupedOpenApi() {
        return GroupedOpenApi
                .builder()
                .displayName("测试")
                .group("test")
                .packagesToScan("com.liyz.boot3.api.user.controller.test")
                .build();
    }
}
