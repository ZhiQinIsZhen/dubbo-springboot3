package com.liyz.boot3.service.search.bo.company;

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
public class CompanyFinancingBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 7747494754239884918L;

    private String id;

    private String companyId;

    private String companyName;

    private Date financingDate;

    private String financingRounds;

    private String valuation;

    private Integer useFlag;

    private String ssqyFlag;

    private String ipo;
}
