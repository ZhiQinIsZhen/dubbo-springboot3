package com.liyz.boot3.api.user.controller.test;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
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

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:39
 */
@Slf4j
@Anonymous
@Tag(name = "测试skywalking")
@RestController
@RequestMapping("/test")
public class TestSkywalkingController {

    @ApiOperationSupport(order = 1)
    @Operation(summary = "你好", tags = "1000")
    @GetMapping("/hello")
    public Result<TestVO> hello(@ParameterObject @Valid TestDTO testDTO) {
        log.info("1111");
        log.info("traceId : {}", TraceIdUtil.getTraceId());
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }
}
