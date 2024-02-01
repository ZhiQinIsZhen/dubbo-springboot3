package com.liyz.boot3.api.user.event.guava;

import lombok.Getter;
import lombok.Setter;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/1 11:11
 */
@Getter
@Setter
public class LoginEvent {

    public LoginEvent(String name) {
        this.name = name;
    }

    private String name;
}
