package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/2/18 14:24
 */
@Slf4j
@Anonymous
@Tag(name = "测试接口版本")
@RestController
@RequestMapping("/test/version")
public class TestVersionController {

    @Operation(summary = "版本1")
    @GetMapping(value = "/getTest", headers = "i-version=1")
    public Result<Boolean> v1() {
        log.info("{}", "v1");
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "版本2")
    @GetMapping("/getTest")
    public Result<Boolean> v2() {
        log.info("{}", "v2");
        return Result.success(Boolean.TRUE);
    }
}
