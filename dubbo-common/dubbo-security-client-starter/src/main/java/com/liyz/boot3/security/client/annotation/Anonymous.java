package com.liyz.boot3.security.client.annotation;

import java.lang.annotation.*;

/**
 * Desc:匿名访问注解
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 13:03
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Anonymous {
}
