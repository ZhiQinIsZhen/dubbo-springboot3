package com.liyz.boot3.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/16 13:16
 */
@EnableAsync
@SpringBootApplication
public class UserApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
}