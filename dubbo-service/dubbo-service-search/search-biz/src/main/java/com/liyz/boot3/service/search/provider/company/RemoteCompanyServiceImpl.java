package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.constant.SearchType;
import com.liyz.boot3.service.search.mapper.CompanyHolderMapper;
import com.liyz.boot3.service.search.mapper.CompanyMapper;
import com.liyz.boot3.service.search.model.CompanyDO;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import com.liyz.boot3.service.search.service.SearchServiceImpl;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:39
 */
@DubboService
public class RemoteCompanyServiceImpl extends SearchServiceImpl<CompanyBO, CompanyPageQuery> implements RemoteCompanyService {

    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private CompanyHolderMapper companyHolderMapper;

    @Override
    protected SearchType getSearchType() {
        return SearchType.COMPANY;
    }

    @Override
    public CompanyBO getById(String id) {
        CompanyDO companyDO = companyMapper.selectById(id);
        return BeanUtil.copyProperties(companyDO, CompanyBO::new);
    }

    @Override
    public List<CompanyBO> getByIds(List<String> ids) {
        return BeanUtil.copyList(companyMapper.selectBatchIds(ids), CompanyBO::new);
    }
}
