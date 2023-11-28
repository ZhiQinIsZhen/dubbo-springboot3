package com.liyz.boot3.api.admin.controller.test;

import com.liyz.boot3.api.admin.dto.test.TestDTO;
import com.liyz.boot3.api.admin.vo.test.TestVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@Tag(name = "测试")
@RestController
@RequestMapping("/test")
public class TestController {

    @Anonymous
    @Operation(summary = "你好")
    @GetMapping("/hello")
    public Result<TestVO> hello(@ParameterObject @Valid TestDTO testDTO) {
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }
}
