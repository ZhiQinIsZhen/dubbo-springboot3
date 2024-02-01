package com.liyz.boot3.api.user.event.spring;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/5 14:52
 */
@Getter
@Setter
public class SearchEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -3047022577230548615L;

    public SearchEvent(Object source) {
        super(source);
    }
}
