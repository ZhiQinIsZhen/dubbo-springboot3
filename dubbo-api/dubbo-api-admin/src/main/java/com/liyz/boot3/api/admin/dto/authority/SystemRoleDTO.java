package com.liyz.boot3.api.admin.dto.authority;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/11 13:23
 */
@Getter
@Setter
public class SystemRoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2197798732348814224L;

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", required = true)
    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称", required = true)
    private String roleName;

    @Schema(description = "父角色ID", required = true)
    private Integer parentRoleId;
}
