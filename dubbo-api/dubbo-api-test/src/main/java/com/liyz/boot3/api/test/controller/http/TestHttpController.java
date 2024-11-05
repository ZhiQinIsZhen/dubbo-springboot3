package com.liyz.boot3.api.test.controller.http;

import com.liyz.boot3.api.test.config.RunRiskIndexProperties;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.service.SschaService;
import com.liyz.boot3.api.test.vo.ssc.CompanyInfoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/20 17:17
 */
@Slf4j
@Tag(name = "http测试")
@RestController
@RequestMapping("/test/http")
public class TestHttpController {

    @Resource
    private SschaService sschaService;

    @Operation(summary = "获取公司基本信息")
    @GetMapping("/getCompanyInfo")
    public Result<CompanyInfoVO> getCompanyInfo(@RequestParam("keyword") String keyword) {
        Mono<Result<CompanyInfoVO>> mono = sschaService.getCompanyInfo(keyword);
        return mono.block();
    }

    @Resource
    private RunRiskIndexProperties properties;


    @Operation(summary = "config")
    @GetMapping("/config")
    public Result<RunRiskIndexProperties> config() {
        return Result.success(properties);
    }
}
