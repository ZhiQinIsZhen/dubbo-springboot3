package com.liyz.boot3.api.admin.controller.test;

import com.liyz.boot3.api.admin.dto.test.TestDTO;
import com.liyz.boot3.api.admin.vo.test.TestVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
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
@ApiResponses({
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "非0都为失败")
})
@RestController
@RequestMapping("/test")
public class TestController {

    @Operation(summary = "你好")
    @GetMapping("/hello")
    public Result<TestVO> hello(@Validated TestDTO testDTO) {
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }
}
