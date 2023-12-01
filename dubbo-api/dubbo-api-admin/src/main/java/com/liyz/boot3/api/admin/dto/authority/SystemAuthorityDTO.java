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
 * @date 2023/3/13 14:33
 */
@Getter
@Setter
public class SystemAuthorityDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 223587002932780274L;

    @NotNull(message = "权限ID不能为空")
    @Schema(description = "权限ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer authorityId;

    @NotBlank(message = "权限码不能为空")
    @Schema(description = "权限码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authority;

    @NotBlank(message = "权限名称不能为空")
    @Schema(description = "权限名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String authorityName;

    @Schema(description = "父权限ID")
    private Integer parentAuthorityId;

    @NotBlank(message = "应用ID不能为空")
    @Schema(description = "应用ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;
}
