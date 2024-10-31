package com.liyz.boot3.api.test.controller.redis;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.liyz.boot3.api.test.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/6/7 17:27
 */
@Slf4j
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

    @Autowired
    private RBlockingQueue<String> blockingQueue;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                try {
                    log.info(blockingQueue.take().toString());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Autowired
    private RDelayedQueue<String> delayedQueue;

    @Operation(summary = "延迟队列")
    @GetMapping("/offer")
    public Result offer(@RequestParam("value") String value, @RequestParam("time") Integer time) {
        log.info("开始时间:{}， 过期时间:{}", System.currentTimeMillis(), System.currentTimeMillis() + time * 1000);
        delayedQueue.offerAsync(value, time, TimeUnit.SECONDS);
        return Result.success();
    }
}
