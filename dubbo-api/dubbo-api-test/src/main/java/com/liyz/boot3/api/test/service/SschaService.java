package com.liyz.boot3.api.test.service;

import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.api.test.vo.ssc.CompanyInfoVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import reactor.core.publisher.Mono;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/20 17:22
 */
@HttpExchange(url = "/services", accept = MediaType.APPLICATION_JSON_VALUE, contentType = MediaType.APPLICATION_JSON_VALUE)
public interface SschaService {

    @GetExchange("/ic/baseinfo")
    Mono<Result<CompanyInfoVO>> getCompanyInfo(@RequestParam("keyword") String keyword);
}
