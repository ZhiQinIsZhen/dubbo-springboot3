package com.liyz.boot3.api.test.controller.valid;

import cn.hutool.extra.validation.BeanValidationResult;
import cn.hutool.extra.validation.ValidationUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.liyz.boot3.api.test.dto.lock.TestDTO;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.util.BeanUtil;
import com.liyz.boot3.api.test.vo.lock.TestVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/16 10:59
 */
@Slf4j
//@Valid
//@Validated
@Tag(name = "测试数据校验")
@RestController
@RequestMapping("/test/validation")
public class TestValidationController {

    @ApiOperationSupport(order = 1)
    @Operation(summary = "valid验证--有效")
    @GetMapping("/valid")
    public Result<TestVO> valid(@ParameterObject @Valid TestDTO testDTO) {
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }

    @ApiOperationSupport(order = 2)
    @Operation(summary = "validated验证--无效")
    @GetMapping("/validated")
    public Result<TestVO> validated(@ParameterObject @Validated TestDTO testDTO) {
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }

    @ApiOperationSupport(order = 3)
    @Operation(summary = "validated group验证--有效")
    @GetMapping("/validated/group")
    public Result<TestVO> validatedGroup(@ParameterObject @Validated({TestDTO.Test1.class}) TestDTO testDTO) {
        return Result.success(BeanUtil.copyProperties(testDTO, TestVO::new));
    }

    @ApiOperationSupport(order = 4)
    @Operation(summary = "form验证--无效")
    @GetMapping("/from")
    public Result<TestVO> from(@NotBlank(message = "名字不能为空") String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 5)
    @Operation(summary = "form方法加valid验证--无效")
    @GetMapping("/from/method/valid")
    public Result<TestVO> fromMethodValid(@Valid @NotBlank(message = "名字不能为空") String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 6)
    @Operation(summary = "form方法加Validated验证--无效")
    @GetMapping("/from/method/validated")
    public Result<TestVO> fromMethodValidated(@Validated @NotBlank(message = "名字不能为空") String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 7)
    @Operation(summary = "form方法加Validated group验证--无效")
    @GetMapping("/from/method/validated/group")
    public Result<TestVO> fromMethodValidatedGroup(@Validated({TestDTO.Test1.class}) @NotBlank(message = "名字不能为空", groups = {TestDTO.Test1.class}) String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 8)
    @Operation(summary = "form类上加valid验证--无效")
    @GetMapping("/from/class/valid")
    public Result<TestVO> fromClassValid(@NotBlank(message = "名字不能为空") String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 9)
    @Operation(summary = "form类上加Validated验证--有效")
    @GetMapping("/from/class/validated")
    public Result<TestVO> fromClassValidated(@NotBlank(message = "名字不能为空") String name) {
        TestVO testVO = new TestVO();
        testVO.setName(name);
        return Result.success(testVO);
    }

    @ApiOperationSupport(order = 10)
    @Operation(summary = "手动验证Validated--no group")
    @GetMapping("/from/manual")
    public Result<BeanValidationResult> fromManual() {
        TestDTO testDTO = new TestDTO();
        return Result.success(ValidationUtil.warpValidate(testDTO));
    }

    @ApiOperationSupport(order = 11)
    @Operation(summary = "手动验证Validated--have group")
    @GetMapping("/from/manual/group")
    public Result<BeanValidationResult> fromManualGroup() {
        TestDTO testDTO = new TestDTO();
        return Result.success(ValidationUtil.warpValidate(testDTO, TestDTO.Test1.class));
    }
}
