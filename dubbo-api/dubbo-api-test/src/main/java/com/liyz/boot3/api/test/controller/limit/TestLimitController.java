package com.liyz.boot3.api.test.controller.limit;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.liyz.boot3.api.test.exception.TestBlockLimitException;
import com.liyz.boot3.api.test.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
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
 * @date 2024/5/18 14:44
 */
@Slf4j
@Tag(name = "限流测试")
@RestController
@RequestMapping("/test/limit")
public class TestLimitController {

    @Resource
    private RedissonClient redissonClient;

    @Operation(summary = "limit")
    @GetMapping("/token")
    public Result<Boolean> testLimit(@RequestParam("num") Long num) {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter("token");
        boolean trySetRate = rRateLimiter.trySetRate(RateType.OVERALL, Math.max(1, num), 1, RateIntervalUnit.MINUTES);
        boolean expire = rRateLimiter.expire(Duration.of(2, ChronoUnit.MINUTES));
        log.info("expire:{},trySetRate:{}", expire, trySetRate);
        return Result.success(rRateLimiter.tryAcquire());
    }

    @Operation(summary = "sentinel限流")
    @GetMapping("/sentinel")
    @SentinelResource(value = "test", blockHandler = "handlerException", blockHandlerClass = TestBlockLimitException.class)
    public Result<Boolean> testLimit() {
        return Result.success(Boolean.TRUE);
    }
}
