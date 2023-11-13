package com.liyz.boot3.common.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 11:04
 */
@Getter
@Setter
public class PageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码", defaultValue = "1")
    @NotNull(groups = {PageQuery.class}, message = "分页查询页码不能为空")
    private Long pageNum = 1L;

    @Schema(description = "每页数量", defaultValue = "10")
    @NotNull(groups = {PageQuery.class}, message = "分页查询每页数量不能为空")
    private Long pageSize = 10L;

    public interface PageQuery {}

    public interface Query {}
}
