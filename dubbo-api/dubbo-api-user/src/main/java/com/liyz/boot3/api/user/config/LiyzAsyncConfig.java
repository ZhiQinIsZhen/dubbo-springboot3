package com.liyz.boot3.api.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/8 16:49
 */
@Slf4j
@Configuration
@EnableAsync
public class LiyzAsyncConfig implements AsyncConfigurer {

    @Bean
    public ThreadPoolTaskExecutor liyzAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors() - 6);
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() - 6);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("liyzAsync-");
        executor.setDaemon(true);
        return executor;
    }

    @Override
    public Executor getAsyncExecutor() {
        return liyzAsyncExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> {
            log.error("error", ex);
        };
    }
}
