package com.liyz.boot3.api.test.vo.lock;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

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

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "手机号码")
    private String mobile;


//    private String multiple;

    @NotBlank
    public String getMultiple() {
        return new StringBuilder().append(Objects.nonNull(name) ? name : "").append(Objects.nonNull(mobile) ? mobile : "").toString();
    }
}
