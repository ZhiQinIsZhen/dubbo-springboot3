package com.liyz.boot3.api.admin.dto.authority;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * @date 2023/3/13 13:40
 */
@Getter
@Setter
public class StaffRoleDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5019855188001537438L;

    @Schema(description = "员工ID")
    private Long staffId;

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer roleId;
}
