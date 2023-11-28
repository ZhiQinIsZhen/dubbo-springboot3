package com.liyz.boot3.gateway;

import com.liyz.boot3.common.api.config.WebMvcAutoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 10:51
 */
@SpringBootApplication(exclude = {WebMvcAutoConfig.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}