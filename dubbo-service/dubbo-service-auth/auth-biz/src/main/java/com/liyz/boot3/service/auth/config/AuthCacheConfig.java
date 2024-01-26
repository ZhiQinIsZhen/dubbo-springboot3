package com.liyz.boot3.service.auth.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.liyz.boot3.common.service.constant.CommonServiceConstant;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/25 15:59
 */
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class AuthCacheConfig {

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

//    @Bean("redissonSpringCacheManager")
    public CacheManager redissonSpringCacheManager(RedissonClient redissonClient, CacheProperties cacheProperties) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        Map<String, CacheConfig> configMap = new HashMap<>();
        long defaultTtl = redisProperties.getTimeToLive() != null ? redisProperties.getTimeToLive().toMillis() : Duration.ofMinutes(60).toMillis();
        for (String cacheName : cacheProperties.getCacheNames()) {
            CacheConfig cacheConfig = new CacheConfig(defaultTtl, 0);
            cacheConfig.setMaxSize(20);
            configMap.put(cacheName, cacheConfig);
        }
        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, configMap, JsonJacksonCodec.INSTANCE);
        cacheManager.setTransactionAware(true);
        cacheManager.setCacheNames(cacheProperties.getCacheNames());
        return cacheManager;
    }

    @Bean("caffeineCacheManager")
    CaffeineCacheManager caffeineCacheManager(CacheProperties cacheProperties) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCacheNames(cacheProperties.getCacheNames());
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder();
        cacheManager.setCaffeine(caffeine);
        return cacheManager;
    }
}
