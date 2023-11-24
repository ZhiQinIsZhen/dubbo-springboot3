package com.liyz.boot3.api.admin.controller.staff;

import com.liyz.boot3.api.admin.dto.staff.StaffLogPageDTO;
import com.liyz.boot3.api.admin.vo.staff.StaffInfoVO;
import com.liyz.boot3.api.admin.vo.staff.StaffLoginLogVO;
import com.liyz.boot3.api.admin.vo.staff.StaffLogoutLogVO;
import com.liyz.boot3.common.api.dto.PageDTO;
import com.liyz.boot3.common.api.result.PageResult;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.staff.bo.StaffInfoBO;
import com.liyz.boot3.service.staff.bo.StaffLoginLogBO;
import com.liyz.boot3.service.staff.bo.StaffLogoutLogBO;
import com.liyz.boot3.service.staff.remote.RemoteStaffInfoService;
import com.liyz.boot3.service.staff.remote.RemoteStaffLoginLogService;
import com.liyz.boot3.service.staff.remote.RemoteStaffLogoutLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/16 16:38
 */
@Tag(name = "员工信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/staff")
public class StaffInfoController {

    @DubboReference
    private RemoteStaffInfoService remoteStaffInfoService;
    @DubboReference
    private RemoteStaffLoginLogService remoteStaffLoginLogService;
    @DubboReference
    private RemoteStaffLogoutLogService remoteStaffLogoutLogService;

    @Operation(summary = "查询当前登录员工信息")
    @GetMapping("/current")
    public Result<StaffInfoVO> userInfo() {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        return Result.success(BeanUtil.copyProperties(remoteStaffInfoService.getByStaffId(authUserBO.getAuthId()), StaffInfoVO::new));
    }

//    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:STAFFINFO'.toUpperCase())")
    @Operation(summary = "分页查询员工信息")
    @GetMapping("/page")
    public PageResult<StaffInfoVO> page(PageDTO page) {
        RemotePage<StaffInfoBO> remotePage = remoteStaffInfoService.page(BeanUtil.copyProperties(page, PageBO::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, StaffInfoVO::new));
    }

//    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:STAFFLOG'.toUpperCase())")
    @Operation(summary = "分页查询员工登录日志")
    @GetMapping("/loginLogs/page")
    public PageResult<StaffLoginLogVO> pageLoginLogs(StaffLogPageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new StaffLogPageDTO();
        page.setStaffId(Objects.nonNull(page.getStaffId()) ? page.getStaffId() : authUserBO.getAuthId());
        RemotePage<StaffLoginLogBO> remotePage = remoteStaffLoginLogService.page(page.getStaffId(), BeanUtil.copyProperties(page, PageBO::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, StaffLoginLogVO::new));
    }

//    @PreAuthorize("hasAuthority('DUBBO-API-ADMIN:STAFFLOG'.toUpperCase())")
    @Operation(summary = "分页查询员工登出日志")
    @GetMapping("/logoutLogs/page")
    public PageResult<StaffLogoutLogVO> pageLogoutLogs(StaffLogPageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new StaffLogPageDTO();
        page.setStaffId(Objects.nonNull(page.getStaffId()) ? page.getStaffId() : authUserBO.getAuthId());
        RemotePage<StaffLogoutLogBO> remotePage = remoteStaffLogoutLogService.page(page.getStaffId(), BeanUtil.copyProperties(page, PageBO::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, StaffLogoutLogVO::new));
    }
}
