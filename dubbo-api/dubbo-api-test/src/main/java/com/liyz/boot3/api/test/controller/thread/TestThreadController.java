package com.liyz.boot3.api.test.controller.thread;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.service.impl.TestServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/16 10:59
 */
@Slf4j
@Tag(name = "测试线程")
@RestController
@RequestMapping("/test/thread")
public class TestThreadController {

    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private TestServiceImpl testService;

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

    @ApiOperationSupport(order = 3)
    @Operation(summary = "优雅停机")
    @GetMapping("/graceful")
    public Result<Boolean> graceful() {
        log.info("开始测试");
        try {
            Thread.sleep(80000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("测试结束");
        return Result.success(Boolean.TRUE);
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "优雅停机1")
    @GetMapping("/graceful1")
    public Result<Boolean> graceful1() {
        log.info("开始测试");
        try {
            Thread.sleep(40000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("测试结束");
        return Result.success(Boolean.TRUE);
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "并行任务")
    @GetMapping("/forkJoin")
    public Result<Boolean> forkJoin() {
        log.info("开始测试");
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    log.info("1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("2");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("3");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("4");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("5");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("6");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("7");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("8");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("9");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool()),
                CompletableFuture.runAsync(() -> {
                    log.info("10");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, ForkJoinPool.commonPool())
        ).join();
        log.info("测试结束");
        return Result.success(Boolean.TRUE);
    }


    @ApiOperationSupport(order = 6)
    @Operation(summary = "并行任务1")
    @GetMapping("/forkJoin1")
    public Result<Boolean> forkJoin1() {
        log.info("开始测试");
        CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> {
                    log.info("1");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("2");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("3");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("4");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("5");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("6");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("7");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("8");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("9");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor),
                CompletableFuture.runAsync(() -> {
                    log.info("10");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }, threadPoolTaskExecutor)
        ).join();
        log.info("测试结束");
        return Result.success(Boolean.TRUE);
    }

    @ApiOperationSupport(order = 7)
    @Operation(summary = "异步调用")
    @GetMapping("/testAsync")
    public Result<Boolean> testAsync(@RequestParam("keyword") String keyword) {
        testService.testAsync(keyword);
        return Result.success(Boolean.TRUE);
    }

    @ApiOperationSupport(order = 8)
    @Operation(summary = "同步调用")
    @GetMapping("/testSync")
    public Result<Boolean> testSync(@RequestParam("keyword") String keyword) {
        testService.test(keyword);
        return Result.success(Boolean.TRUE);

    }
}
