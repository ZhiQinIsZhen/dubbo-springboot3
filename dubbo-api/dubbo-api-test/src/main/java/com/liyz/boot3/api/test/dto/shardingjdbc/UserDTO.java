package com.liyz.boot3.api.test.dto.shardingjdbc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/11 19:26
 */
@Getter
@Setter
public class UserDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8556474743882878296L;

    private Long userId;
}
