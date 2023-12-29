package com.liyz.boot3.service.user.config;

import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/28 13:43
 */
@Configuration
public class ProviderConfig {

    @Bean("task-executor")
    public Executor taskExecutor() {
        return new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory("task"));
    }
}
