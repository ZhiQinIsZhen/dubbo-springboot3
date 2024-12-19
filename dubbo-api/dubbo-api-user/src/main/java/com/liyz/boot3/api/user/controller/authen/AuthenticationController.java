package com.liyz.boot3.api.user.controller.authen;

import com.google.common.eventbus.EventBus;
import com.liyz.boot3.api.user.dto.authen.UserLoginDTO;
import com.liyz.boot3.api.user.dto.authen.UserRegisterDTO;
import com.liyz.boot3.api.user.event.guava.LoginEvent;
import com.liyz.boot3.api.user.vo.authen.AuthLoginVO;
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
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/6/15 16:15
 */
@Tag(name = "客户鉴权")
@ApiResponses(value = {
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "失败")
})
@Slf4j
@RestController
@RequestMapping("/user")
public class AuthenticationController {

    @Anonymous
    @Operation(summary = "注册")
    @PostMapping("/register")
    public Result<Boolean> register(@Validated({UserRegisterDTO.Register.class}) @RequestBody UserRegisterDTO staffRegister) {
        return Result.success(AuthContext.AuthService.registry(BeanUtil.copyProperties(staffRegister, AuthUserRegisterBO::new)));
    }

    @Resource
    private EventBus eventBus;

    @Anonymous
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Validated({UserLoginDTO.Login.class}) @RequestBody UserLoginDTO loginDTO) throws IOException {
        AuthUserBO authUserBO = AuthContext.AuthService.login(BeanUtil.copyProperties(loginDTO, LoginBO::new));
        return Result.success(BeanUtil.copyProperties(authUserBO, AuthLoginVO::new, (s, t) -> {
                    eventBus.post(new LoginEvent(s.getUsername()));
                    t.setExpiration(AuthContext.JwtService.getExpiration(s.getToken()));
                }));
    }

    @Operation(summary = "登出")
    @GetMapping(value = "/logout")
    public Result<Boolean> logout() {
        return Result.success(AuthContext.AuthService.logout());
    }
}
