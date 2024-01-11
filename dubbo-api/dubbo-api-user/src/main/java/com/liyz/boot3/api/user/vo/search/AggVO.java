package com.liyz.boot3.api.user.vo.search;

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
 * @date 2023/11/14 15:16
 */
@Getter
@Setter
public class AggVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4672225002891936839L;

    @Schema(description = "列")
    private String name;

    @Schema(description = "值")
    private Object value;

}
