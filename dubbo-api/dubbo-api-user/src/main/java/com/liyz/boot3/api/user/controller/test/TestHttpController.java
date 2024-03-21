package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.api.user.vo.test.CompanyInfoVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
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
@Anonymous
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
}
