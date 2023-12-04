package com.liyz.boot3.common.util.annotation;

import com.liyz.boot3.common.util.constant.DesensitizationType;

import java.lang.annotation.*;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/5/24 15:33
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Desensitization {

    /**
     * 脱敏类型
     *
     * @return 脱敏类型
     */
    DesensitizationType value() default DesensitizationType.DEFAULT;

    /**
     * 开始坐标
     *
     * <p>1.beginIndex=-1,endIndex=3,则表示最后3位脱敏
     * <p>2.beginIndex=3,endIndex=-1,则表示前3位脱敏
     * <p>3.beginIndex=1,endIndex=2,则表示第一位到第二位脱敏
     * <p>4.beginIndex=-1,endIndex=-1,则表示全部字符脱敏
     *
     * @return 开始坐标值
     */
    int beginIndex() default 0;

    /**
     * 结束坐标
     *
     * @return 结束坐标值
     */
    int endIndex() default 0;
}
