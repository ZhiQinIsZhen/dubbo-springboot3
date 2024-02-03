package com.liyz.boot3.service.search.remote.company;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.search.bo.company.CompanyBO;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:37
 */
public interface RemoteCompanyService {

    CompanyBO getById(String id);

    List<CompanyBO> getByIds(List<String> ids);

    CompanyBO selectOne(CompanyBO companyBO);

    RemotePage<CompanyBO> selectPage(PageBO pageBO, CompanyBO companyBO);
}
