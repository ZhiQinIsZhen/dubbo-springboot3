package com.liyz.boot3.api.test.controller.redis;

import com.liyz.boot3.api.test.dto.redis.TestScoreDTO;
import com.liyz.boot3.api.test.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/24 14:22
 */
@Slf4j
@Tag(name = "测试Redis zet")
@RestController
@RequestMapping("/test/zset")
public class TestZSetController {

    @Resource
    private RedissonClient redissonClient;

    private static final String TOC_KEY = "ssc:news:toc";
    private static final Codec DEFAULT_CODEC = new StringCodec();

    @Operation(summary = "添加")
    @GetMapping("/add")
    public Result<Boolean> add(TestScoreDTO scoreDTO) {
        RScoredSortedSet<Long> sortedSet = redissonClient.getScoredSortedSet(TOC_KEY);
        sortedSet.addAsync(scoreDTO.getScore().doubleValue(), scoreDTO.getId());
        return Result.success(Boolean.TRUE);
    }

    @Operation(summary = "获取第一个poll")
    @GetMapping("/poll/getFirst")
    public Result<Long> getFirstPoll() {
        RScoredSortedSet<Long> sortedSet = redissonClient.getScoredSortedSet(TOC_KEY);
        return Result.success(sortedSet.pollFirst());
    }

    @Operation(summary = "获取第一个take")
    @GetMapping("/take/getFirst")
    public Result<Long> getFirstTake() {
        RScoredSortedSet<Long> sortedSet = redissonClient.getScoredSortedSet(TOC_KEY);
        return Result.success(sortedSet.takeFirst());
    }

    @Operation(summary = "获取第一个分数")
    @GetMapping("/score/getFirst")
    public Result<Double> getFirstScore() {
        RScoredSortedSet<Long> sortedSet = redissonClient.getScoredSortedSet(TOC_KEY);
        return Result.success(sortedSet.firstScore());
    }

    @Operation(summary = "获取所有值")
    @GetMapping("/getAll")
    public Result<List<Long>> getAll() {
        RScoredSortedSet<Long> sortedSet = redissonClient.getScoredSortedSet(TOC_KEY);
        return Result.success(new ArrayList<>(sortedSet.readAll()));
    }
}
