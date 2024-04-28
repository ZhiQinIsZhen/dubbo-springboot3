package com.liyz.boot3.api.user.dto.test;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/24 14:24
 */
@Getter
@Setter
public class TestScoreDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -265429768907635071L;

    private Long score;

    private Long id;
}
