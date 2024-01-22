package com.liyz.boot3.api.user.vo.search.company;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 9:34
 */
@Getter
@Setter
public class CompanyBasicVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 170535601509514115L;

    @Schema(description = "主键")
    private String id;

    private String name;

    private String regNumber;

    private String creditCode;

    private String legalPersonName;

    private Long type;

    private Long companyType;

    private String regCapital;

    private String estiblishTime;
}
