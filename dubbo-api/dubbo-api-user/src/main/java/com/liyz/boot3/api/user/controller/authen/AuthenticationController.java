package com.liyz.boot3.api.user.controller.authen;

import com.liyz.boot3.api.user.dto.authen.UserLoginDTO;
import com.liyz.boot3.api.user.dto.authen.UserRegisterDTO;
import com.liyz.boot3.api.user.vo.authen.AuthLoginVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.api.util.HttpServletContext;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.security.client.constant.SecurityClientConstant;
import com.liyz.boot3.security.client.context.AuthContext;
import com.liyz.boot3.service.auth.bo.AuthUserBO;
import com.liyz.boot3.service.auth.bo.AuthUserLoginBO;
import com.liyz.boot3.service.auth.bo.AuthUserRegisterBO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @Anonymous
    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Validated({UserLoginDTO.Login.class}) @RequestBody UserLoginDTO loginDTO) throws IOException {
        AuthUserBO authUserBO = AuthContext.AuthService.login(BeanUtil.copyProperties(loginDTO, AuthUserLoginBO::new));
        AuthLoginVO authLoginVO = new AuthLoginVO();
        authLoginVO.setToken(authUserBO.getToken());
        authLoginVO.setExpiration(AuthContext.JwtService.getExpiration(authUserBO.getToken()));
        if (StringUtils.isNotBlank(loginDTO.getRedirect())) {
            HttpServletResponse response = HttpServletContext.getResponse();
            response.setHeader(SecurityClientConstant.DEFAULT_TOKEN_HEADER_KEY, authLoginVO.getToken());
            response.sendRedirect(loginDTO.getRedirect());
        }
        return Result.success(authLoginVO);
    }

    @Operation(summary = "登出")
    @PostMapping("/logout")
    @Parameter(name = "Authorization", in = ParameterIn.HEADER, description = "认证token", required = true, example = "Bearer ")
    public Result<Boolean> logout() {
        return Result.success(AuthContext.AuthService.logout());
    }
}
