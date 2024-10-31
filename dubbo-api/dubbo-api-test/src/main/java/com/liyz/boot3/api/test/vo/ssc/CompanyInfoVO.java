package com.liyz.boot3.api.test.vo.ssc;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/20 17:28
 */
@Getter
@Setter
public class CompanyInfoVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4939904243121363469L;

    private String companyId;
    private String companyName;
    private String legalPerson;
    private String companyCode;
    private String creditNo;
    private String orgCode;
    private String logo;
    private Integer insuranceCount;
    private String insuranceCountFlag;
    private String companyTypeDesc;
    private String companyType;
    private String statusFlag;
    private String authority;
    private String industryFirstCode;
    private String operationEndTime;
    private BigDecimal capitalAmount;
    private String capitalUnit;
    private String address;
    private String businessScope;
    private String generalProject;
    private String licensedProject;
    private String cancelTime;
    private BigDecimal realCapitalAmount;
    private String realCapitalUnit;
    private String taxType;
    private String legalPersonType;
    private String miniCompanyFlag;
    private String enName;
    private String provinceShort;
    private String taxCode;
    private List<String> oldName;
    private String cancelReason;
    private String revokeTime;
    private String revokeReason;
    private String stockCode;
    private String province;
    private String city;
    private String district;
    private String areaCode;
    private String capitalTypeFlag;
}
