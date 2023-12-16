package com.liyz.boot3.common.api.util;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/16 13:32
 */
public class I18nMessageUtil implements ApplicationListener<ContextRefreshedEvent> {

    private static ResourceBundleMessageSource messageSource;

    /**
     * 获取国际化消息
     *
     * @param code code
     * @param defaultMessage 默认消息
     * @param args 参数
     * @return 国际化消息
     */
    public static String getMessage(String code, String defaultMessage, String... args) {
        return messageSource.getMessage(code, args, defaultMessage, LocaleContextHolder.getLocale());
    }

    /**
     * 获取国际化消息
     *
     * @param code code
     * @param args 参数
     * @return 国际化消息
     */
    public static String getMessage(String code, String... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        messageSource = event.getApplicationContext().getBean(ResourceBundleMessageSource.class);
    }
}
