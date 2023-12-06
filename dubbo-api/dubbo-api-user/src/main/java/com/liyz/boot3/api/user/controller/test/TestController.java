package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.api.user.dto.test.TestDTO;
import com.liyz.boot3.api.user.vo.test.TestVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.common.util.TraceIdUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:39
 */
@Slf4j
@Anonymous
@Tag(name = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "你好")
    @GetMapping("/hello")
    public Result<TestVO> hello(@ParameterObject @Valid TestDTO testDTO) {
        log.info("1111");
        log.info("traceId : {}", TraceIdUtil.getTraceId());
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }

    @Operation(summary = "虚拟线程")
    @GetMapping("/virtual/thread")
    public Result<Boolean> virtualThread() {
        try(ExecutorService es = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0, j = 10; i < j; i++) {
                es.submit(() -> {
                    try {
                        log.info("before");
                        Thread.sleep(1000);
                        log.info("after");
                    } catch (Exception e) {

                    }
                });
            }
        }
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "正常线程")
    @GetMapping("/natual/thread")
    public Result<Boolean> natualThread() {
        try(ExecutorService es = Executors.newFixedThreadPool(1)) {
            for (int i = 0, j = 10; i < j; i++) {
                es.submit(() -> {
                    try {
                        log.info("before");
                        Thread.sleep(1000);
                        log.info("after");
                    } catch (Exception e) {

                    }
                });
            }
        }
        return Result.success(Boolean.TRUE);
    }
}
