package com.liyz.boot3.service.search.remote.company;

import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.SearchService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:37
 */
public interface RemoteCompanyService extends SearchService<CompanyBO, CompanyPageQuery> {

    CompanyBO selectOne(CompanyBO companyBO);
}
