package com.liyz.boot3.api.user.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/12 9:39
 */
@Getter
@Setter
public class SscStaffExcel implements Serializable {
    @Serial
    private static final long serialVersionUID = -9070950674078062343L;

    @ExcelProperty(value = "公司名称", index = 0)
    private String companyName;

    @ExcelProperty(value = "高管名称", index = 1)
    private String employeeName;

    @ExcelProperty(value = "职务", index = 2)
    private String position;

    @ExcelProperty(value = "法人", index = 3)
    private String legalPerson;
}
