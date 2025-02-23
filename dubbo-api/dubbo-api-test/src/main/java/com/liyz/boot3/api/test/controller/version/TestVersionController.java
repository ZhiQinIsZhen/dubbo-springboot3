package com.liyz.boot3.api.test.controller.version;

import com.liyz.boot3.api.test.result.Result;
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
@Tag(name = "测试接口版本")
@RestController
@RequestMapping("/test/version")
public class TestVersionController {

    private static final String HEADER_KEY_PREFIX = "I-VERSION=";
    private static final String I_VERSION_1 = "1";

    @Operation(summary = "版本1")
    @GetMapping(value = "/getTest", headers = HEADER_KEY_PREFIX + I_VERSION_1)
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
