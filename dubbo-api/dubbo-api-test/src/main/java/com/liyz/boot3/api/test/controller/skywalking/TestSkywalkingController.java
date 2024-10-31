package com.liyz.boot3.api.test.controller.skywalking;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.liyz.boot3.api.test.dto.lock.TestDTO;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.util.BeanUtil;
import com.liyz.boot3.api.test.vo.lock.TestVO;
import com.liyz.boot3.common.util.TraceIdUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:39
 */
@Slf4j
@Tag(name = "测试skywalking")
@RestController
@RequestMapping("/test")
public class TestSkywalkingController {

    @Operation(summary = "你好", tags = "1000")
    @GetMapping("/hello")
    public Result<TestVO> hello(@ParameterObject @Valid TestDTO testDTO) {
        log.info("1111");
        log.info("traceId : {}", TraceIdUtil.getTraceId());
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }

    @Operation(summary = "你好1", tags = "1000")
    @GetMapping("/hello1")
    public Result<String> hello1() throws InterruptedException {
        Thread.sleep(Duration.of(5, ChronoUnit.SECONDS));
        log.info("traceId : {}", TraceIdUtil.getTraceId());
        return Result.success(TraceIdUtil.getTraceId());
    }

    @Operation(summary = "你好2")
    @GetMapping("/hello2")
    public Result<Boolean> hello2(@RequestParam("str") String str) {
        return Result.success(str.chars().anyMatch(c -> PinyinUtil.isChinese((char) c)));
    }
}
