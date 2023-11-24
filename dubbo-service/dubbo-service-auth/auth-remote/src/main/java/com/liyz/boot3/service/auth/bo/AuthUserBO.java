package com.liyz.boot3.service.auth.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/24 10:41
 */
@Getter
@Setter
public class AuthUserBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6046110472442516409L;

    /**
     * 用户id
     */
    private Long authId;
}
