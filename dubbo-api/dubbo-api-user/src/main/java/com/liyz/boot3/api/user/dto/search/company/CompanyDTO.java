package com.liyz.boot3.api.user.dto.search.company;

import com.liyz.boot3.common.api.dto.PageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 10:27
 */
@Getter
@Setter
public class CompanyDTO extends PageDTO {
    @Serial
    private static final long serialVersionUID = 310955426454050170L;

    @Schema(description = "公司名称", defaultValue = "阿里巴巴")
    @NotBlank(message = "公司名称不能为空")
    private String companyName;
}
