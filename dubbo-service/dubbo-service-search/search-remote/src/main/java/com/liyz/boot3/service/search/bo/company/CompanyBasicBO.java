package com.liyz.boot3.service.search.bo.company;

import com.liyz.boot3.service.search.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 9:34
 */
@Getter
@Setter
public class CompanyBasicBO extends BaseBO {
    @Serial
    private static final long serialVersionUID = 170535601509514115L;

    private String name;

    private String regNumber;

    private String creditCode;

    private String legalPersonName;

    private Long type;

    private Long companyType;

    private String regCapital;

    private String estiblishTime;
}
