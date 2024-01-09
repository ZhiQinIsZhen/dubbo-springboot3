package com.liyz.boot3.api.user.dto.search.finance;

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
 * @date 2024/1/9 14:25
 */
@Getter
@Setter
public class CompanyFinancingDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2476194349406602709L;

    private String companyId;

    private String financingRounds;
}
