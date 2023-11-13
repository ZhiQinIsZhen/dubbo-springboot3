package com.liyz.boot3.common.api.result;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.liyz.boot3.common.remote.exception.CommonExceptionCodeEnum;
import com.liyz.boot3.common.remote.exception.IExceptionService;
import com.liyz.boot3.common.remote.page.RemotePage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:23
 */
@Getter
@Setter
@JsonPropertyOrder({"code", "message", "total", "pages", "pageNum", "pageSize", "hasNextPage", "data"})
public class PageResult<T> {

    public PageResult() {}

    public PageResult(String code, String message) {
        this.code = code;
        this.message = message;
        this.total = 0L;
        this.pageNum = 1L;
        this.pageSize = 10L;
    }

    public PageResult(RemotePage<T> data) {
        boolean isNull = data == null;
        this.setData(isNull ? List.of() : data.getList());
        this.total = isNull ? 0L : data.getTotal();
        this.pageNum = isNull ? 1 : data.getPageNum();
        this.pageSize = isNull ? 10 : data.getPageSize();
        this.code = CommonExceptionCodeEnum.SUCCESS.getCode();
        this.message = CommonExceptionCodeEnum.SUCCESS.getMessage();
    }

    @Schema(description = "code码")
    private String code;

    @Schema(description = "消息")
    private String message;

    @Schema(description = "总数量")
    private Long total;

    @Schema(description = "页码")
    private Long pageNum;

    @Schema(description = "每页条数")
    private Long pageSize;

    @Schema(description = "数据体")
    private List<T> data;

    public static <T> PageResult<T> success(RemotePage<T> data) {
        return new PageResult<>(data);
    }

    public static <T> PageResult<T> error(String code, String message) {
        return new PageResult<T>(code, message);
    }

    public static <T> PageResult<T> error(IExceptionService codeEnum) {
        return new PageResult<T>(codeEnum.getCode(), codeEnum.getMessage());
    }

    public void setPageNum(long pageNum) {
        this.pageNum = Math.max(1L, pageNum);
    }

    public void setPageSize(long pageSize) {
        this.pageSize = Math.max(1L, pageSize);
    }

    public long getPages() {
        return this.total % this.pageSize == 0 ? this.total / this.pageSize : this.total / this.pageSize + 1;
    }

    public boolean isHasNextPage() {
        return getPages() > pageNum;
    }
}
