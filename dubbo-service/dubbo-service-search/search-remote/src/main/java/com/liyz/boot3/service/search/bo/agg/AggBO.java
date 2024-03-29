package com.liyz.boot3.service.search.bo.agg;

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
public class AggBO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4672225002891936839L;

    private String name;

    private Object value;

    public static AggBO of(String name, Object value) {
        AggBO aggBO = new AggBO();
        aggBO.setName(name);
        aggBO.setValue(value);
        return aggBO;
    }
}
