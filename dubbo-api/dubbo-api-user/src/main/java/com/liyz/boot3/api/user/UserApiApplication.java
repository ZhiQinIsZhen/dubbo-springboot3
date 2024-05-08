package com.liyz.boot3.api.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/16 13:16
 */
@EnableAsync
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
public class UserApiApplication {

    public static void main(String[] args) {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        SpringApplication.run(UserApiApplication.class, args);
    }
}