package com.liyz.boot3.api.user.dto.test;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 13:10
 */
@Getter
@Setter
public class TestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2923451254947704868L;

    @Schema(description = "名字")
    @NotBlank(message = "名字不能为空")
    private String name;

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号码不能为空", groups = {Test1.class})
    private String mobile;

    @Schema(description = "年龄")
    private Integer age;

    public interface Test1 {}
}
