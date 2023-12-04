package com.liyz.boot3.common.util.annotation;

import java.lang.annotation.*;

/**
 * Desc:该优先级比 validation 参数校验注解 高
 * 即 先处理 Trim 再 处理 validation
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/9 10:29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Trim {

}
