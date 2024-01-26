package com.liyz.boot3.service.user.config;

import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import org.apache.dubbo.common.utils.NamedThreadFactory;
import org.redisson.api.RedissonClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
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
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class UserConfig {

    @Bean("task-executor")
    public Executor taskExecutor() {
        return new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new NamedThreadFactory("task"));
    }

    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        return RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheName -> cacheName + CommonServiceConstant.DEFAULT_JOINER)
                .prefixCacheNameWith(redisProperties.getKeyPrefix())
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(redisProperties.getTimeToLive() != null ? redisProperties.getTimeToLive() : Duration.ofMinutes(60));
    }

    @Bean
    @Primary
    public CacheManager cacheManager(RedissonClient redissonClient, CacheProperties cacheProperties) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(new RedissonConnectionFactory(redissonClient))
                .cacheDefaults(redisCacheConfiguration(cacheProperties))
                .transactionAware()
                .build();
    }
}
