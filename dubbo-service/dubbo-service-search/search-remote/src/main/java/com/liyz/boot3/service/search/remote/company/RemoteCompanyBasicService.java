package com.liyz.boot3.service.search.remote.company;

import com.liyz.boot3.service.search.bo.company.CompanyBasicBO;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/20 15:54
 */
public interface RemoteCompanyBasicService {

    CompanyBasicBO selectOne(CompanyBasicBO companyBO);
}
