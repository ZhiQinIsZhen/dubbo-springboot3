package com.liyz.boot3.api.user.controller.test;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/6/7 17:27
 */
@Slf4j
@Anonymous
@Tag(name = "redis测试")
@RestController
@RequestMapping("/test/redis")
public class TestRedisController {

    private static final String ORDER_PREFIX = "SO";

    @Resource
    private RedissonClient redissonClient;

    @Operation(summary = "获取id")
    @GetMapping("/getId")
    public Result<Long> getId(@RequestParam("step") Integer step) {
        String dateStr = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATE_FORMAT);
        RAtomicLong atomicLong = redissonClient.getAtomicLong(ORDER_PREFIX + ":" + dateStr);
        Long value;
        if (atomicLong.isExists()) {
            value = atomicLong.addAndGet(Math.max(1, step));
        } else {
            value = atomicLong.addAndGet(Math.max(1, step));
            atomicLong.expire(Duration.ofDays(1));
        }
        return Result.success(value);
    }
}
