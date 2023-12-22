package com.liyz.boot3.api.admin.controller.authority;

import com.liyz.boot3.api.admin.dto.authority.StaffRoleDTO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.staff.bo.StaffRoleBO;
import com.liyz.boot3.service.staff.remote.RemoteStaffRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/16 15:25
 */
@Tag(name = "员工权限信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/staff/authority")
public class StaffAuthorityController {

    @DubboReference
    private RemoteStaffRoleService remoteStaffRoleService;

    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:STAFF-BIND-ROLE'.toUpperCase())")
    @Operation(summary = "给员工绑定一个角色")
    @PostMapping("/bindRole")
//    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<Boolean> bindRole(@RequestBody @Validated StaffRoleDTO staffRoleDTO) {
        AuthUserBO authUser = AuthContext.getAuthUser();
        staffRoleDTO.setStaffId(Objects.nonNull(staffRoleDTO.getStaffId()) ? staffRoleDTO.getStaffId() : authUser.getAuthId());
        remoteStaffRoleService.bindRole(BeanUtil.copyProperties(staffRoleDTO, StaffRoleBO::new));
        return Result.success(Boolean.TRUE);
    }
}
