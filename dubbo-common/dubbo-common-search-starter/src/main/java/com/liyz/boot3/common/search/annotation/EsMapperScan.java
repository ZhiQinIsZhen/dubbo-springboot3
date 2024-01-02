package com.liyz.boot3.common.search.annotation;

import com.liyz.boot3.common.search.config.EsMapperScannerRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Desc:es mapper scan
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 15:02
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({EsMapperScannerRegistrar.class})
public @interface EsMapperScan {

    @AliasFor("basePackages")
    String[] value() default {};

    @AliasFor("value")
    String[] basePackages() default {};
}
