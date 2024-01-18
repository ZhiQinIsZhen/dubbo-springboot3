package com.liyz.boot3.api.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/17 13:27
 */
@EnableAdminServer
@SpringBootApplication
public class MonitorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApiApplication.class, args);
    }
}