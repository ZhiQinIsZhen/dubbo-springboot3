package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.common.remote.page.PageBO;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.search.query.LambdaQueryWrapper;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.mapper.CompanyMapper;
import com.liyz.boot3.service.search.model.CompanyDO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 10:39
 */
@DubboService
public class RemoteCompanyServiceImpl implements RemoteCompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public CompanyBO getById(String id) {
        CompanyDO companyDO = companyMapper.selectById(id, new LambdaQueryWrapper<>(CompanyDO.class)
                .select(CompanyDO::getCompanyId, CompanyDO::getCompanyCode));
        return BeanUtil.copyProperties(companyDO, CompanyBO::new);
    }

    @Override
    public List<CompanyBO> getByIds(List<String> ids) {
        List<CompanyDO> doList = companyMapper.selectBatchIds(ids,
                new LambdaQueryWrapper<>(CompanyDO.class)
                        .select(CompanyDO::getCompanyId)
        );
        return BeanUtil.copyList(doList, CompanyBO::new);
    }

    @Override
    public CompanyBO selectOne(CompanyBO companyBO) {
        CompanyDO companyDO = companyMapper.selectOne(lqw -> lqw
                .setEntityClass(CompanyDO.class)
                .term("company_name_tag.raw", companyBO.getCompanyNameTag())
        );
        return BeanUtil.copyProperties(companyDO, CompanyBO::new);
    }

    @Override
    public RemotePage<CompanyBO> selectPage(PageBO pageBO, CompanyBO companyBO) {
        RemotePage<CompanyDO> doRemotePage = companyMapper.selectPage(pageBO, lqw -> lqw.setEntityClass(CompanyDO.class));
        return BeanUtil.copyRemotePage(doRemotePage, CompanyBO::new);
    }
}
