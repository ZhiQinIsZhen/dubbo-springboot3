package com.liyz.boot3.api.user.event.listener;

import com.liyz.boot3.api.user.event.SearchEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/5 15:00
 */
@Slf4j
@Component
public class SearchEventListener implements ApplicationListener<SearchEvent> {

    @Resource
    private ApplicationContext applicationContext;

    @Async
    @Override
    public void onApplicationEvent(SearchEvent event) {
        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class,
                false, false);
        log.info("触发了search event");
    }
}
