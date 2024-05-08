package com.liyz.boot3.process.flowable;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:启动类
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/28 11:16
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.liyz.boot3.process.flowable.dao"})
public class FlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableApplication.class, args);
    }
}