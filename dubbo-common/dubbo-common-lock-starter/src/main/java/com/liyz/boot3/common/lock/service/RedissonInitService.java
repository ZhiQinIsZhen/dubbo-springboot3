package com.liyz.boot3.common.lock.service;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.redisson.spring.starter.RedissonProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/10/19 9:55
 */
public interface RedissonInitService extends CommonInitService {

    /**
     * 获取定制器列表
     *
     * @return 定制器列表
     */
    List<RedissonAutoConfigurationCustomizer> getCustomizers();

    /**
     * redissonClient bean create
     * @see RedissonAutoConfiguration
     *
     * @param redissonProperties redisson配置
     * @param redisProperties redis配置
     * @return redissonClient
     */
    default RedissonClient redissonClient(final RedissonProperties redissonProperties, final RedisProperties redisProperties) {
        int database = 10;

        Integer timeout = null;
        if (redisProperties.getTimeout() != null) {
            timeout = (int)redisProperties.getTimeout().toMillis();
        }
        Integer connectTimeout = null;
        if (redisProperties.getConnectTimeout() != null) {
            connectTimeout = (int)redisProperties.getConnectTimeout().toMillis();
        }

        Config config;
        if (redissonProperties.getConfig() != null) {
            try {
                config = Config.fromYAML(redissonProperties.getConfig());
            } catch (IOException var20) {
                try {
                    config = Config.fromJSON(redissonProperties.getConfig());
                } catch (IOException var19) {
                    var19.addSuppressed(var20);
                    throw new IllegalArgumentException("Can't parse config", var19);
                }
            }
        }  else if (redissonProperties.getFile() != null) {
            try {
                InputStream is = this.getConfigStream(redissonProperties.getFile());
                config = Config.fromYAML(is);
            } catch (IOException var18) {
                try {
                    InputStream is = this.getConfigStream(redissonProperties.getFile());
                    config = Config.fromJSON(is);
                } catch (IOException var17) {
                    var17.addSuppressed(var18);
                    throw new IllegalArgumentException("Can't parse config", var17);
                }
            }
        } else if (redisProperties.getSentinel() != null) {
            config = new Config();
            SentinelServersConfig c = config
                    .useSentinelServers()
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .addSentinelAddress(this.convert(redisProperties.getSentinel().getNodes()))
                    .setDatabase(database)
                    .setUsername(redisProperties.getUsername())
                    .setPassword(redisProperties.getPassword())
                    .setClientName(redisProperties.getClientName());
            if (connectTimeout != null) {
                c.setConnectTimeout(connectTimeout);
            }

            if (connectTimeout != null && timeout != null) {
                c.setTimeout(timeout);
            }
        } else {
            if (redisProperties.getClientName() != null) {
                String[] nodes = this.convert(redisProperties.getCluster().getNodes());
                config = new Config();
                ClusterServersConfig c = config
                        .useClusterServers()
                        .addNodeAddress(nodes)
                        .setUsername(redisProperties.getUsername())
                        .setPassword(redisProperties.getPassword())
                        .setClientName(redisProperties.getClientName());
                if (connectTimeout != null) {
                    c.setConnectTimeout(connectTimeout);
                }
            } else {
                config = new Config();
                String prefix = redisProperties.getSsl().isEnabled() ? "rediss://" : "redis://";
                SingleServerConfig c = config
                        .useSingleServer()
                        .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                        .setDatabase(database)
                        .setUsername(redisProperties.getUsername())
                        .setPassword(redisProperties.getPassword())
                        .setClientName(redisProperties.getClientName());
                if (connectTimeout != null) {
                    c.setConnectTimeout(connectTimeout);
                }

                if (connectTimeout != null && timeout != null) {
                    c.setTimeout(timeout);
                }
            }
        }

        if (getCustomizers() != null) {
            for (RedissonAutoConfigurationCustomizer customizer : getCustomizers()) {
                customizer.customize(config);
            }
        }

        return Redisson.create(config);
    }

    /**
     * 节点信息转化
     *
     * @param nodesObject 节点list
     * @return 节点数组
     */
    default String[] convert(List<String> nodesObject) {
        return nodesObject
                .stream()
                .map(node -> !node.startsWith("redis://") && !node.startsWith("rediss://") ? "redis://" + node : node)
                .toArray(String[]::new);
    }
}
