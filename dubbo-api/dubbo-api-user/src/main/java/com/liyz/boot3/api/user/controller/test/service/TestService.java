package com.liyz.boot3.api.user.controller.test.service;

import com.liyz.boot3.api.user.controller.test.SschaService;
import com.liyz.boot3.api.user.vo.test.CompanyInfoVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.util.JsonMapperUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/8 17:21
 */
@Slf4j
@Service
public class TestService {

    @Resource
    private SschaService sschaService;

    @Async
    public void testAsync(String keyword) {
        Mono<Result<CompanyInfoVO>> mono = sschaService.getCompanyInfo(keyword);
        CompanyInfoVO vo = mono.block().getData();
        log.info("vo : {}", JsonMapperUtil.toJSONPrettyString(vo));
    }

    public CompanyInfoVO test(String keyword) {
        Mono<Result<CompanyInfoVO>> mono = sschaService.getCompanyInfo(keyword);
        CompanyInfoVO vo = mono.block().getData();
        log.info("vo : {}", JsonMapperUtil.toJSONPrettyString(vo));
        return vo;
    }

}
