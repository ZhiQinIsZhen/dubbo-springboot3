package com.liyz.boot3.service.search.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 14:05
 */
@Getter
@Setter
public class CompanyFinancingExcel implements Serializable {
    @Serial
    private static final long serialVersionUID = 7747494754239884918L;

    @ExcelProperty(value = "id")
    private String id;

    @ExcelProperty(value = "公司ID")
    private String companyId;

    @ExcelProperty(value = "公司名称")
    private String companyName;

    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "融资日期")
    private Date financingDate;

    @ExcelProperty(value = "融资轮次")
    private String financingRounds;

    @ExcelProperty(value = "估值")
    private String valuation;

    @ExcelProperty(value = "是否删除")
    private Integer useFlag;
}
