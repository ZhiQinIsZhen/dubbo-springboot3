package com.liyz.boot3.service.search.query.company;

import com.liyz.boot3.service.search.query.PageQuery;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:38
 */
@Getter
@Setter
public class CompanyPageQuery extends PageQuery {
    @Serial
    private static final long serialVersionUID = -1225057130309668169L;

    private String companyName;
}
