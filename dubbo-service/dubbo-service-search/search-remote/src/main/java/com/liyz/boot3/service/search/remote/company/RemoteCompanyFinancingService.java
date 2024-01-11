package com.liyz.boot3.service.search.remote.company;

import com.liyz.boot3.service.search.bo.agg.AggBO;
import com.liyz.boot3.service.search.bo.company.CompanyFinancingBO;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 14:10
 */
public interface RemoteCompanyFinancingService {

    CompanyFinancingBO selectOne(CompanyFinancingBO financingBO);

    void export(List<String> companyIds);

    void export1(List<String> companyNames);

    List<AggBO> agg(CompanyFinancingBO financingBO);
}
