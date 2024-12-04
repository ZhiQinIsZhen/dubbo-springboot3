package com.liyz.boot3.api.test.controller.lock;

import com.liyz.boot3.api.test.dto.lock.TestDTO;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.util.BeanUtil;
import com.liyz.boot3.api.test.vo.lock.TestVO;
import com.liyz.boot3.common.lock.util.RedisLockUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/16 10:59
 */
@Slf4j
@Tag(name = "测试锁")
@RestController
@RequestMapping("/test/lock")
public class TestLockController {

//    @ApiOperationSupport(order = 1)
    @Operation(summary = "锁")
    @GetMapping("/lock")
    public Result<TestVO> lock(@ParameterObject @Valid TestDTO testDTO) {
        TestVO testVO = RedisLockUtil.lock("111", 1, TimeUnit.MINUTES, true, () -> BeanUtil.copyProperties(testDTO, TestVO::new));
        Pair<Boolean, TestVO> pair = RedisLockUtil.tryLock("222", 50, -1, TimeUnit.SECONDS, true, () ->{
            try {
                log.info("step 1");
                Thread.sleep(testDTO.getAge() * 10000);
                log.info("step 2");
            } catch (InterruptedException e) {
                log.info("step 3");
                throw new RuntimeException(e);
            }
            return BeanUtil.copyProperties(testDTO, TestVO::new);
        });
        return Result.success(testVO);
    }
}
