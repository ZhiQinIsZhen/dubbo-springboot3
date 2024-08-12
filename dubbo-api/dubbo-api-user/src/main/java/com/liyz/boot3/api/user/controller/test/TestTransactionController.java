package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.util.DateUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.user.bo.UserLoginLogBO;
import com.liyz.boot3.service.user.remote.RemoteUserLoginLogService;
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
 * @date 2024/7/30 16:43
 */
@Slf4j
@Anonymous
@Tag(name = "测试事务")
@RestController
@RequestMapping("/test/transaction")
public class TestTransactionController {

    @DubboReference
    private RemoteUserLoginLogService remoteUserLoginLogService;

    @Operation(summary = "测试")
    @GetMapping("/testTransaction")
    public Result<Boolean> testTransaction() {
        UserLoginLogBO userLoginLogBO = new UserLoginLogBO();
        userLoginLogBO.setUserId(100L);
        userLoginLogBO.setDevice(1);
        userLoginLogBO.setIp("127.0.0.1");
        userLoginLogBO.setLoginTime(DateUtil.currentDate());
        remoteUserLoginLogService.insert(userLoginLogBO);
        return Result.success(Boolean.TRUE);
    }
}
