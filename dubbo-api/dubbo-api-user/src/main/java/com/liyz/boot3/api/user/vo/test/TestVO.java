package com.liyz.boot3.api.user.vo.test;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 13:09
 */
@Getter
@Setter
public class TestVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2803393452733281029L;

    @Schema(description = "名字")
    private String name;
}
