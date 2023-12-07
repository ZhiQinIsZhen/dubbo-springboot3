package com.liyz.boot3.common.lock.config;

import com.liyz.boot3.common.lock.service.RedissonInitService;
import com.liyz.boot3.common.lock.util.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/12 14:35
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({RedissonProperties.class, RedisProperties.class})
public class LockAutoConfig implements RedissonInitService, InitializingBean {

    private final RedissonProperties redissonProperties;
    private final RedisProperties redisProperties;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired(required = false)
    private List<RedissonAutoConfigurationCustomizer> customizers;

    public LockAutoConfig(RedissonProperties redissonProperties, RedisProperties redisProperties) {
        this.redissonProperties = redissonProperties;
        this.redisProperties = redisProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RedisLockUtil.setRedissonClient(this.redissonClient(redissonProperties, redisProperties));
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public List<RedissonAutoConfigurationCustomizer> getCustomizers() {
        return this.customizers;
    }
}
