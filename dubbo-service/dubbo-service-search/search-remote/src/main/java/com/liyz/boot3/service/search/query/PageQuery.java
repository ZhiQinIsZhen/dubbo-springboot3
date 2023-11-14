package com.liyz.boot3.service.search.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:34
 */
@Getter
@Setter
public class PageQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = -7798227940102881776L;

    /**
     * 页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数据条数
     */
    private Integer pageSize = 20;

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * list查询最大数量
     */
    private Integer listMaxCount = 50;

    /**
     * 跟踪命中数
     */
    private Integer trackTotalHits = 1000;

    /**
     * 权重
     */
    private Integer slop;
}
