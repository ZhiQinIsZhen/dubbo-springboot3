package com.liyz.boot3.security.client.annotation;

import java.lang.annotation.*;

/**
 * 注释:token解析user信息注解
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/12/16 22:08
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUser {
}
