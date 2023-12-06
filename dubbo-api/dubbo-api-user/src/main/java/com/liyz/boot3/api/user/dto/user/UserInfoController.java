package com.liyz.boot3.api.user.dto.user;

import com.liyz.boot3.api.user.vo.user.UserInfoVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.user.remote.RemoteUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @DubboReference
    private RemoteUserInfoService remoteUserInfoService;

    @Operation(summary = "查询当前登录用户信息")
    @GetMapping("/current")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<UserInfoVO> userInfo() {
        AuthUserBO authUserBO = AuthContext.getAuthUser();
        return Result.success(BeanUtil.copyProperties(remoteUserInfoService.getByUserId(authUserBO.getAuthId()), UserInfoVO::new));
    }
}
