package com.liyz.boot3.api.user.event.guava.listener;

import com.google.common.eventbus.Subscribe;
import com.liyz.boot3.api.user.event.guava.LoginEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/1 11:12
 */
@Slf4j
public class LoginEventListener {

    @Subscribe
    public void handleLoginEvent(LoginEvent loginEvent) {
        log.info("{} 上线啦！！！！", loginEvent.getName());
    }
}
