package com.liyz.boot3.service.search.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 16:25
 */
@Getter
@Setter
public class BaseBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6971827342998500218L;

    private String id;
}
