package com.liyz.boot3.api.admin.controller.authentication;

import com.liyz.boot3.api.admin.dto.authentication.StaffLoginDTO;
import com.liyz.boot3.api.admin.dto.authentication.StaffRegisterDTO;
import com.liyz.boot3.api.admin.vo.authentication.AuthLoginVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.security.client.bo.LoginBO;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserRegisterBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 16:15
 */
@Tag(name = "员工鉴权")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Anonymous
    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Boolean> register(@Validated({StaffRegisterDTO.Register.class}) @RequestBody StaffRegisterDTO staffRegister) {
        return Result.success(AuthContext.AuthService.registry(BeanUtil.copyProperties(staffRegister, AuthUserRegisterBO::new)));
    }

    @Anonymous
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Validated({StaffLoginDTO.Login.class}) @RequestBody StaffLoginDTO loginDTO) throws IOException {
        AuthUserBO authUserBO = AuthContext.AuthService.login(BeanUtil.copyProperties(loginDTO, LoginBO::new));
        return Result.success(BeanUtil.copyProperties(authUserBO, AuthLoginVO::new, (s, t) ->
                        t.setExpiration(AuthContext.JwtService.getExpiration(s.getToken()))));
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Boolean> logout() {
        return Result.success(AuthContext.AuthService.logout());
    }
}
