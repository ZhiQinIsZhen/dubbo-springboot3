package com.liyz.boot3.service.search.bo.investor;

import com.liyz.boot3.service.search.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:36
 */
@Getter
@Setter
public class CompanyInvestorBO extends BaseBO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2524475764297876678L;

    private String companyId;

    private String companyName;

    private String investorName;

    private String establishTime;

    private String mark;

    private String logo;
}
