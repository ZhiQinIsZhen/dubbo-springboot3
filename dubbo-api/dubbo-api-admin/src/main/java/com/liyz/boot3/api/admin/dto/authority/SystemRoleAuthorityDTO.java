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
 * @date 2023/3/11 15:33
 */
@Getter
@Setter
public class SystemRoleAuthorityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8328113920009535585L;

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID", required = true)
    private Integer roleId;

    @NotNull(message = "权限ID不能为空")
    @Schema(description = "权限ID", required = true)
    private Integer authorityId;
}
