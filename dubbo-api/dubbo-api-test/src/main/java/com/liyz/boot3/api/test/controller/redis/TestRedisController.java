package com.liyz.boot3.api.test.controller.redis;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Lists;
import com.liyz.boot3.api.test.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.redisson.client.RedisException;
import org.redisson.client.codec.StringCodec;
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


    @Operation(summary = "lua脚本")
    @GetMapping("/lua")
    public Result<Boolean> lua() {
        Integer change = 2;
        try {
            Object eval = redissonClient.getScript(StringCodec.INSTANCE).eval(
                    RScript.Mode.READ_WRITE,
                    getLuaScript(),
                    RScript.ReturnType.INTEGER,
                    Lists.newArrayList("stock:1", "log:1"),
                    change,
                    "type_1",
                    1,
                    2,
                    com.liyz.boot3.common.util.DateUtil.currentDate().getTime(),
                    "333",
                    1);
        } catch (RedisException e) {
            log.error("lua执行异常", e);
        }
        return Result.success(Boolean.TRUE);
    }

    private String getLuaScript() {
        return "if redis.call('hexists', KEYS[2], ARGV[2]) == 1 then\n"
                + "    return redis.error_reply('OPERATION_ALREADY_EXECUTED')\n"
                + "end\n"
                + "local current = redis.call('get', KEYS[1])\n"
                + "if current == false then\n"
                + "    return redis.error_reply('KEY_NOT_FOUND')\n"
                + "end\n"
                + "if tonumber(current) == nil then\n"
                + "    return redis.error_reply('current value is not a number')\n"
                + "end\n"
                + "local change = tonumber(ARGV[1])\n"
                + "if change == nil then\n"
                + "    return redis.error_reply('change value is not a number')\n"
                + "end\n"
                + "local new = tonumber(current) + change\n"
                + "redis.call('set', KEYS[1], tostring(new))\n"
                + "-- 获取Redis服务器的当前时间（秒和微秒）\n"
                + "local time = redis.call('time')\n"
                + "-- 转换为毫秒级时间戳\n"
                + "local currentTimeMillis = (time[1] * 1000) + math.floor(time[2] / 1000)\n"
                + "-- 使用哈希结构存储日志\n"
                + "redis.call('hset', KEYS[2], ARGV[2], cjson.encode({\n"
                + "    action = '3',\n"
                + "    from = current,\n"
                + "    to = new,\n"
                + "    change = ARGV[1],\n"
                + "    skuNo = ARGV[3],\n"
                + "    subOrderNo = ARGV[4],\n"
                + "    bizTime = ARGV[5],\n"
                + "    orderNo = ARGV[6],\n"
                + "    bizType = ARGV[7],\n"
                + "    timestamp = currentTimeMillis\n"
                + "}))\n"
                + "return new";
    }
}
