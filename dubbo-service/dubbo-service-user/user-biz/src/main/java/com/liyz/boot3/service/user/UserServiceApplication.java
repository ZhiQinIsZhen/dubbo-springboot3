package com.liyz.boot3.service.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 16:49
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.liyz.boot3.service.*.dao"})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}