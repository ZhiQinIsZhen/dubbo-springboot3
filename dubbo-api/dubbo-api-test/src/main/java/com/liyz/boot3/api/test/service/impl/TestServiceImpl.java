package com.liyz.boot3.api.test.service.impl;

import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.service.SschaService;
import com.liyz.boot3.api.test.vo.ssc.CompanyInfoVO;
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
 * @date 2024/10/31 13:38
 */
@Slf4j
@Service
public class TestServiceImpl {

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
