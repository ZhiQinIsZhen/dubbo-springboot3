package com.liyz.boot3.service.search.provider.investor;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.search.Query.LambdaQueryWrapper;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.investor.CompanyInvestorBO;
import com.liyz.boot3.service.search.mapper.CompanyInvestorMapper;
import com.liyz.boot3.service.search.model.CompanyInvestorDO;
import com.liyz.boot3.service.search.query.PageQuery;
import com.liyz.boot3.service.search.remote.investor.RemoteCompanyInvestorService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:45
 */
@DubboService
public class RemoteCompanyInvestorServiceImpl implements RemoteCompanyInvestorService {

    @Resource
    private CompanyInvestorMapper companyInvestorMapper;

    @Override
    public CompanyInvestorBO getById(String id) {
        return BeanUtil.copyProperties(companyInvestorMapper.selectById(id), CompanyInvestorBO::new);
    }

    @Override
    public RemotePage<CompanyInvestorBO> page(PageQuery pageQuery) {
        RemotePage<CompanyInvestorDO> doRemotePage = companyInvestorMapper.selectPage(PageBO.of(pageQuery.getPageNum(), pageQuery.getPageSize()), new LambdaQueryWrapper<>(CompanyInvestorDO.class).term(CompanyInvestorDO::getCompanyId, pageQuery.getCompanyId()));
        return BeanUtil.copyRemotePage(doRemotePage, CompanyInvestorBO::new);
    }
}
