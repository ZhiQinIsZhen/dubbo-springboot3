package com.liyz.boot3.service.search.remote.investor;

import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.service.search.bo.investor.CompanyInvestorBO;
import com.liyz.boot3.service.search.query.PageQuery;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:41
 */
public interface RemoteCompanyInvestorService {

    CompanyInvestorBO getById(String id);

    RemotePage<CompanyInvestorBO> page(PageQuery pageQuery);
}
