package com.liyz.boot3.api.user.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
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

    @Resource
    private RedissonClient redissonClient;

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

    @Bean
    public RBlockingQueue<String> blockingQueue() {
        return redissonClient.getBlockingQueue("test");
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(RBlockingQueue<String> blockingQueue) {
        return redissonClient.getDelayedQueue(blockingQueue);
    }

//    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
