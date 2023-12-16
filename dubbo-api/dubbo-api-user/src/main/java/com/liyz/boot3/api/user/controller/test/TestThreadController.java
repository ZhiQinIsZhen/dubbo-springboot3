package com.liyz.boot3.api.user.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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
 * @date 2023/12/16 10:59
 */
@Slf4j
@Anonymous
@Tag(name = "测试线程")
@RestController
@RequestMapping("/test/thread")
public class TestThreadController {

    @ApiOperationSupport(order = 1)
    @Operation(summary = "正常线程")
    @GetMapping("/natual")
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

    @ApiOperationSupport(order = 2)
    @Operation(summary = "虚拟线程")
    @GetMapping("/virtual")
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


}
