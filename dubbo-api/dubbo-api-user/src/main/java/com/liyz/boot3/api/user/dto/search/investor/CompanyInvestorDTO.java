package com.liyz.boot3.api.user.dto.search.investor;

import com.liyz.boot3.common.api.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:52
 */
@Getter
@Setter
public class CompanyInvestorDTO extends PageDTO {
    @Serial
    private static final long serialVersionUID = 4580544315167900762L;

    @Schema(description = "公司ID")
    private String companyId;
}
