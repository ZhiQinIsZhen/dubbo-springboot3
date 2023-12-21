package com.liyz.boot3.common.dao.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.liyz.boot3.common.dao.handler.MybatisPlusMetaObjectHandler;
import com.liyz.boot3.common.dao.interceptor.MapperParamInterceptor;
import com.liyz.boot3.common.dao.interceptor.MapperResultInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/23 17:06
 */
@Configuration
@EnableConfigurationProperties(DesensitizationProperties.class)
public class MybatisPlusAutoConfig {

    /**
     * 配置插件
     * 注:官网文档说分页插件最好放在最后一个，并且如果确定DbType，则配上
     *
     * @return 插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //乐观锁插件:实体字段带@Version则有乐观锁功能
        mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新与删除
        mybatisPlusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //分页插件
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

    @Bean
    public MybatisPlusMetaObjectHandler mybatisPlusMetaObjectHandler() {
        return new MybatisPlusMetaObjectHandler();
    }

    @Bean
    @ConditionalOnProperty(prefix = "desensitization.database", name = "enable", havingValue = "true")
    public MapperParamInterceptor mapperParamInterceptor() {
        return new MapperParamInterceptor();
    }

    @Bean
    @ConditionalOnProperty(prefix = "desensitization.database", name = "enable", havingValue = "true")
    public MapperResultInterceptor mapperResultInterceptor() {
        return new MapperResultInterceptor();
    }
}
