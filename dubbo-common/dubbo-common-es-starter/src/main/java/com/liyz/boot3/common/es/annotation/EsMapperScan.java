package com.liyz.boot3.common.es.annotation;

import com.liyz.boot3.common.es.mapper.EsMapperFactoryBean;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/25 16:48
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({EsMapperScannerRegistrar.class})
public @interface EsMapperScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};

    Class<? extends BeanNameGenerator> nameGenerator() default BeanNameGenerator.class;

    Class<?> markerInterface() default Class.class;

    Class<? extends EsMapperFactoryBean> factoryBean() default EsMapperFactoryBean.class;
}
