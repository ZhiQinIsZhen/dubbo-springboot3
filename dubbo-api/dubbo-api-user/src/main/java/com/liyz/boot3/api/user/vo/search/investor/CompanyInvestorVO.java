package com.liyz.boot3.api.user.vo.search.investor;

import com.liyz.boot3.service.search.bo.BaseBO;
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
 * @date 2024/1/8 13:36
 */
@Getter
@Setter
public class CompanyInvestorVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2524475764297876678L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "公司ID")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "投资机构名称")
    private String investorName;

    @Schema(description = "机构成立时间")
    private String establishTime;

    @Schema(description = "机构介绍")
    private String mark;

    @Schema(description = "logo")
    private String logo;
}
