package com.liyz.boot3.service.search.query.agg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 15:21
 */
@Getter
@Setter
public class AggQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = -5163642946677800494L;

    private String field;

    private int size = 100;

    private int minDocCount = 1;

    /**
     * _count
     * _key
     */
    private String order;

    private boolean desc;
}
