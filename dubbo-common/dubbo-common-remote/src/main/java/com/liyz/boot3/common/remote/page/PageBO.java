package com.liyz.boot3.common.remote.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/13 10:28
 */
@Getter
@Setter
public class PageBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 页码
     */
    private Long pageNum = 1L;

    /**
     * 每页条数
     */
    private Long pageSize = 10L;

    public static PageBO of(long pageNum, long pageSize) {
        PageBO pageBO = new PageBO();
        pageBO.setPageSize(Math.max(pageNum, 1L));
        pageBO.setPageSize(Math.max(pageSize, 1L));
        return pageBO;
    }
}
