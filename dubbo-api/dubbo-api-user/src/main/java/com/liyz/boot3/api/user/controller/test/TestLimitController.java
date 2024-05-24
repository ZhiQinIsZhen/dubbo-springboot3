package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
@Anonymous
@Tag(name = "限流测试")
@RestController
@RequestMapping("/test/limit")
public class TestLimitController {

    @Resource
    private RedissonClient redissonClient;

    @Operation(summary = "limit")
    @GetMapping("/token")
    public Result<Boolean> testLimit() {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter("token");
        boolean trySetRate = rRateLimiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.MINUTES);
        boolean expire = rRateLimiter.expire(Duration.of(2, ChronoUnit.MINUTES));
        log.info("expire:{},trySetRate:{}", expire, trySetRate);
        return Result.success(rRateLimiter.tryAcquire());
    }
}
