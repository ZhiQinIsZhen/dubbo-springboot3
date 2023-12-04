package com.liyz.boot3.api.admin.dto.authentication;

import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.common.util.annotation.Trim;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/10 14:24
 */
@Getter
@Setter
public class StaffLoginDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4503927168128475109L;

    @Trim
    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空", groups = {Login.class})
    private String username;

    @Trim
    @Schema(description = "密码，8-20位数字或字母组成", example = "123456789", required = true)
    @Pattern(regexp = PatternUtil.PASSWORD_STRONG, groups = {Login.class}, message = "请输入8到20位数字和字母组合")
    private String password;

    @Schema(description = "重定向路劲")
    private String redirect;

    public interface Login {}
}
