package com.liyz.boot3.common.api.error;

import com.liyz.boot3.common.api.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:50
 */
@Tag(name = "错误重定向")
@ApiResponses({
        @ApiResponse(responseCode = "0", description = "成功"),
        @ApiResponse(responseCode = "1", description = "非0都为失败")
})
@RestController
@RequestMapping("/liyz")
public class ErrorApiController extends BasicErrorController {

    public ErrorApiController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    @Operation(summary = "错误重定向", description = "这是一个用户自定义错误重定向")
    @RequestMapping("/error")
    public Result<String> myError(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        return Result.error(String.valueOf(status.value()), status.getReasonPhrase());
    }
}
