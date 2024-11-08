package com.liyz.boot3.api.test.controller.advice;

import com.liyz.boot3.api.test.dto.lock.TestDTO;
import com.liyz.boot3.api.test.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiYangzhen
 * @version : AdviceController.java, v 0.1 2024-11-08 15:19 LiYangzhen Exp $
 */
@Slf4j
@Tag(name = "advice测试")
@RestController
@RequestMapping("/test/advice")
public class AdviceController {

    @Operation(summary = "get")
    @GetMapping(value = "/get")
    public Result<Boolean> get(TestDTO testDTO) {
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "post")
    @PostMapping(value = "/post")
    public Result<Boolean> post(@RequestBody TestDTO testDTO) {
        return Result.success(Boolean.TRUE);
    }
}
