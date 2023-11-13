package com.liyz.boot3.common.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:07
 */
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Springboot3系统API")
                        .version("1.0")
                        .description( "这是一个基于Apache Dubbo、Springboot3等框架的脚手架")
                        .termsOfService("http://127.0.0.1:7071/")
                        .license(new License().name("MIT License").url("http://127.0.0.1:7071/"))
                        .contact(new Contact()
                                .name("ZhiQinIsZhen")
                                .email("liyangzhen0114@foxmail.com")
                                .url("https://github.com/ZhiQinIsZhen/dubbo-springboot3")
                        )
                );
    }
}
