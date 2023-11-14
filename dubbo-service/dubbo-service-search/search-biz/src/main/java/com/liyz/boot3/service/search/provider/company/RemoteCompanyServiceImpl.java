package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.constant.SearchType;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import com.liyz.boot3.service.search.service.SearchServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:39
 */
@Service
public class RemoteCompanyServiceImpl extends SearchServiceImpl<CompanyBO, CompanyPageQuery> implements RemoteCompanyService {

    @Override
    protected SearchType getSearchType() {
        return SearchType.COMPANY;
    }
}
