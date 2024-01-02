package com.liyz.boot3.common.search.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 15:16
 */
@Getter
@Setter
public class AggResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4672225002891936839L;

    private String name;

    private long count;

    public static AggResponse of(String name, long count) {
        AggResponse aggResponse = new AggResponse();
        aggResponse.setName(name);
        aggResponse.setCount(count);
        return aggResponse;
    }
}
