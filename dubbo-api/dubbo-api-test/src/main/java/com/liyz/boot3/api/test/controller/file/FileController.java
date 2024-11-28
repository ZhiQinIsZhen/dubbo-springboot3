package com.liyz.boot3.api.test.controller.file;

import com.liyz.boot3.api.test.dto.lock.TestDTO;
import com.liyz.boot3.api.test.result.Result;
import com.liyz.boot3.common.util.JsonMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/20 17:17
 */
@Slf4j
@Tag(name = "文件测试")
@RestController
@RequestMapping("/test/file")
public class FileController {

    @Operation(summary = "图片")
    @PostMapping("/image")
    public Result<Boolean> image(@RequestPart("image") MultipartFile image, TestDTO testDTO) {
        log.info(JsonMapperUtil.toJSONString(testDTO));
        log.info("contentType:{}, size:{}", image.getContentType(), image.getSize());
        return Result.success(Boolean.TRUE);
    }
}
