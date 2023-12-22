package com.liyz.boot3.api.admin.controller.authority;

import com.liyz.boot3.api.admin.dto.authority.SystemAuthorityDTO;
import com.liyz.boot3.api.admin.dto.authority.SystemRoleAuthorityDTO;
import com.liyz.boot3.api.admin.dto.authority.SystemRoleDTO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.staff.bo.SystemAuthorityBO;
import com.liyz.boot3.service.staff.bo.SystemRoleAuthorityBO;
import com.liyz.boot3.service.staff.bo.SystemRoleBO;
import com.liyz.boot3.service.staff.remote.RemoteSystemAuthorityService;
import com.liyz.boot3.service.staff.remote.RemoteSystemRoleAuthorityService;
import com.liyz.boot3.service.staff.remote.RemoteSystemRoleService;
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

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/16 15:28
 */
@Tag(name = "系统权限信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/system/authority")
public class SystemAuthorityController {

    @DubboReference
    private RemoteSystemAuthorityService remoteSystemAuthorityService;
    @DubboReference
    private RemoteSystemRoleService remoteSystemRoleService;
    @DubboReference
    private RemoteSystemRoleAuthorityService remoteSystemRoleAuthorityService;

    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:SYSTEM-AUTHORITY-ADD'.toUpperCase())")
    @Operation(summary = "增加一个系统权限码")
    @PostMapping("/addAuthority")
//    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<Boolean> addAuthority(@RequestBody @Validated SystemAuthorityDTO systemAuthorityDTO) {
        remoteSystemAuthorityService.addSystemAuthority(BeanUtil.copyProperties(systemAuthorityDTO, SystemAuthorityBO::new));
        return Result.success(Boolean.TRUE);
    }

    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:SYSTEM-ROLE-ADD'.toUpperCase())")
    @Operation(summary = "增加一个系统角色")
    @PostMapping("/addRole")
//    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<Boolean> addRole(@RequestBody @Validated SystemRoleDTO systemRoleDTO) {
        remoteSystemRoleService.addSystemRole(BeanUtil.copyProperties(systemRoleDTO, SystemRoleBO::new));
        return Result.success(Boolean.TRUE);
    }

    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:AUTHORITY-BIND-ROLE'.toUpperCase())")
    @Operation(summary = "给某个权限绑定一个角色")
    @PostMapping("/bindRole")
//    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<Boolean> bindRole(@RequestBody @Validated SystemRoleAuthorityDTO systemRoleAuthorityDTO) {
        remoteSystemRoleAuthorityService.bindRole(BeanUtil.copyProperties(systemRoleAuthorityDTO, SystemRoleAuthorityBO::new));
        return Result.success(Boolean.TRUE);
    }
}
