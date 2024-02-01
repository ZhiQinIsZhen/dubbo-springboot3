package com.liyz.boot3.api.user.event.guava.config;

import com.google.common.eventbus.EventBus;
import com.liyz.boot3.api.user.event.guava.LoginEvent;
import com.liyz.boot3.api.user.event.guava.listener.LoginEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/1 11:15
 */
@Configuration
public class GuavaEventConfig {

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(new LoginEventListener());
        return eventBus;
    }
}
