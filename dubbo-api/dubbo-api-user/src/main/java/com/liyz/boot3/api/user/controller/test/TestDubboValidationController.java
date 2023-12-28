package com.liyz.boot3.api.user.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.liyz.boot3.api.user.vo.user.UserInfoVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.user.bo.UserInfoBO;
import com.liyz.boot3.service.user.remote.RemoteUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
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
 * @date 2023/12/16 10:59
 */
@Slf4j
@Anonymous
@Tag(name = "测试Dubbo数据校验")
@RestController
@RequestMapping("/test/dubbo/validation")
public class TestDubboValidationController {

    @DubboReference
    private RemoteUserInfoService remoteUserInfoService;

    @ApiOperationSupport(order = 1)
    @Operation(summary = "valid验证")
    @GetMapping("/valid")
    public Result<UserInfoVO> valid() {
        UserInfoBO userInfoBO = remoteUserInfoService.getByUserId(null);
        return Result.success(BeanUtil.copyProperties(userInfoBO, UserInfoVO::new));
    }
}
