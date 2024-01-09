package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.common.search.Query.LambdaQueryWrapper;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.company.CompanyFinancingBO;
import com.liyz.boot3.service.search.mapper.CompanyFinancingMapper;
import com.liyz.boot3.service.search.model.CompanyFinancingDO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyFinancingService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/9 14:12
 */
@DubboService
public class RemoteCompanyFinancingServiceImpl implements RemoteCompanyFinancingService {

    @Resource
    private CompanyFinancingMapper companyFinancingMapper;

    @Override
    public CompanyFinancingBO selectOne(CompanyFinancingBO financingBO) {
        return BeanUtil.copyProperties(companyFinancingMapper.selectOne(new LambdaQueryWrapper<>(CompanyFinancingDO.class)
                        .term(Objects.nonNull(financingBO.getCompanyId()), CompanyFinancingDO::getCompanyId, financingBO.getCompanyId())
                        .term(Objects.nonNull(financingBO.getFinancingRounds()), CompanyFinancingDO::getFinancingRounds, financingBO.getFinancingRounds())
                ), CompanyFinancingBO::new
        );
    }
}
