package com.liyz.boot3.api.user.controller.user;

import com.liyz.boot3.api.user.vo.user.UserInfoVO;
import com.liyz.boot3.api.user.vo.user.UserLoginLogVO;
import com.liyz.boot3.api.user.vo.user.UserLogoutLogVO;
import com.liyz.boot3.common.api.dto.PageDTO;
import com.liyz.boot3.common.api.result.PageResult;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.AuthUser;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.user.bo.UserLoginLogBO;
import com.liyz.boot3.service.user.bo.UserLogoutLogBO;
import com.liyz.boot3.service.user.remote.RemoteUserInfoService;
import com.liyz.boot3.service.user.remote.RemoteUserLoginLogService;
import com.liyz.boot3.service.user.remote.RemoteUserLogoutLogService;
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
 * @date 2023/12/6 10:47
 */
@Tag(name = "用户信息")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
//@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @DubboReference
    private RemoteUserInfoService remoteUserInfoService;
    @DubboReference
    private RemoteUserLoginLogService remoteUserLoginLogService;
    @DubboReference
    private RemoteUserLogoutLogService remoteUserLogoutLogService;

    @Operation(summary = "查询当前登录用户信息")
    @GetMapping("/current")
    public Result<UserInfoVO> userInfo() {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        return Result.success(BeanUtil.copyProperties(remoteUserInfoService.getByUserId(authUserBO.getAuthId()), UserInfoVO::new));
    }

    @Operation(summary = "查询当前登录用户信息--注解式")
    @GetMapping("/current1")
    public Result<UserInfoVO> userInfo1(@AuthUser AuthUserBO authUser) {
        return Result.success(BeanUtil.copyProperties(remoteUserInfoService.getByUserId(authUser.getAuthId()), UserInfoVO::new));
    }

    @Operation(summary = "分页查询用户登录日志")
    @GetMapping("/loginLogs/page")
    public PageResult<UserLoginLogVO> pageLoginLogs(PageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new PageDTO();
        RemotePage<UserLoginLogBO> remotePage = remoteUserLoginLogService.page(authUserBO.getAuthId(), BeanUtil.copyProperties(page, PageBO::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, UserLoginLogVO::new));
    }

    @Operation(summary = "分页查询用户登出日志")
    @GetMapping("/logoutLogs/page")
    public PageResult<UserLogoutLogVO> pageLogoutLogs(PageDTO page) {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        page = Objects.nonNull(page) ? page : new PageDTO();
        RemotePage<UserLogoutLogBO> remotePage = remoteUserLogoutLogService.page(authUserBO.getAuthId(), BeanUtil.copyProperties(page, PageBO::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, UserLogoutLogVO::new));
    }
}
