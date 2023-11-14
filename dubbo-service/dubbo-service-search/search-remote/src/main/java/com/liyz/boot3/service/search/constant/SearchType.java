package com.liyz.boot3.service.search.constant;

import com.liyz.boot3.service.search.bo.bid.BidDetailBO;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Desc:查询类型枚举
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/8/17 15:10
 */
@Getter
@AllArgsConstructor
public enum SearchType {
    COMPANY(CompanyBO.class, "公司基本信息"),
    BID_DETAIL(BidDetailBO.class, "招投标明细"),
    ;

    private final Class<?> clazz;

    private final String desc;
}
