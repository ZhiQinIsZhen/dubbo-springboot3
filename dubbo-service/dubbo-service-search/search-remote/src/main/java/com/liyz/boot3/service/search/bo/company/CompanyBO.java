package com.liyz.boot3.service.search.bo.company;

import com.liyz.boot3.service.search.bo.BaseBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:32
 */
@Getter
@Setter
public class CompanyBO extends BaseBO {
    @Serial
    private static final long serialVersionUID = 1614910778903013522L;

    private String companyId;

    private String companyNameTag;

    private String companyCode;

    private String creditNo;

    private String legalPersonFlag;

    private String addressTag;

    private Long establishmentTime;

    private List<String> honorFlag;

    private String ssqyFlag;
}
