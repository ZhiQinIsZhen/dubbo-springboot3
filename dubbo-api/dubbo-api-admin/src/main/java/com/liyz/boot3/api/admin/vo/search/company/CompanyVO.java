package com.liyz.boot3.api.admin.vo.search.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * @date 2023/11/14 10:32
 */
@Getter
@Setter
public class CompanyVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1614910778903013522L;

    @Schema(description = "主键")
    private String id;

    @Schema(description = "公司ID")
    private String companyId;

    @Schema(description = "公司名称")
    private String companyNameTag;

    @Schema(description = "公司注册码")
    private String companyCode;

    @Schema(description = "统一信用代码")
    private String creditNo;

    @Schema(description = "法人名称")
    private String legalPersonFlag;

    @Schema(description = "注册地址")
    private String addressTag;

    @Schema(description = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date establishmentTime;
}
