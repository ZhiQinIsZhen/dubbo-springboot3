package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.util.PatternUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/5/9 10:33
 */
@Slf4j
@Anonymous
@Tag(name = "正则表达式-test")
@RestController
@RequestMapping("/test/reg")
public class PatternController {

    /**
     * <p><img src="https://boss-testa.qjdchina.com/fs/file/download?fileKey=df0788ad-cc5d-4aa1-8a98-bd2b035f2dc8"></p>
     *
     * @param html
     * @return
     */
    @Operation(summary = "html标签")
    @GetMapping("/html")
    public Result<String> html(@RequestParam("html") String html) {
        return Result.success(html.replaceAll(PatternUtil.HTML_REG, StringUtils.EMPTY));
    }

    @Operation(summary = "jsoup标签")
    @GetMapping("/jsoup/html")
    public Result<String> jsoupHtml(@RequestParam("html") String html) {
        return Result.success(Jsoup.parse(html).text());
    }

    @Operation(summary = "jsoup标签")
    @GetMapping("/jsoup/url")
    public Result<String> jsoupUrl(@RequestParam("url") String url) throws IOException {
        URL url1 = new URL(url);
        return Result.success(Jsoup.parse(url1, 4000).text());
    }
}
