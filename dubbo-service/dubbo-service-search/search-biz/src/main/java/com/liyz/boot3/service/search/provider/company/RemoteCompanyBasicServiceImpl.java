package com.liyz.boot3.service.search.provider.company;

import com.liyz.boot3.common.search.Query.LambdaQueryWrapper;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.company.CompanyBasicBO;
import com.liyz.boot3.service.search.mapper.CompanyBasicMapper;
import com.liyz.boot3.service.search.model.CompanyBasicDO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyBasicService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/20 15:55
 */
@DubboService
public class RemoteCompanyBasicServiceImpl implements RemoteCompanyBasicService {

    @Resource
    private CompanyBasicMapper companyBasicMapper;

    @Override
    public CompanyBasicBO selectOne(CompanyBasicBO companyBO) {
        return BeanUtil.copyProperties(companyBasicMapper.selectOne(new LambdaQueryWrapper<>(CompanyBasicDO.class)
                .term("creditCode.keyword", "91420106MA4KN55P8T")), CompanyBasicBO::new);
    }
}
