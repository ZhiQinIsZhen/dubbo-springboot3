package com.liyz.boot3.service.search.provider.company;

import cn.idev.excel.EasyExcel;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import com.liyz.boot3.common.search.query.LambdaQueryWrapper;
import com.liyz.boot3.common.search.response.AggResponse;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.agg.AggBO;
import com.liyz.boot3.service.search.bo.company.CompanyFinancingBO;
import com.liyz.boot3.service.search.excel.CompanyFinancingExcel;
import com.liyz.boot3.service.search.mapper.CompanyFinancingMapper;
import com.liyz.boot3.service.search.mapper.CompanyMapper;
import com.liyz.boot3.service.search.model.CompanyDO;
import com.liyz.boot3.service.search.model.CompanyFinancingDO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyFinancingService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.ArrayList;
import java.util.List;
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
//                        .terms(Objects.isNull(financingBO.getFinancingRounds()), CompanyFinancingDO::getFinancingRounds, List.of("IPO", "上市", "主板", "新三板", "新四板"))
                        .sort(CompanyFinancingDO::getFinancingDate, SortOrder.Desc)
                ), CompanyFinancingBO::new
        );
    }

    @Override
    public void export(List<String> companyIds) {
        CompanyFinancingBO financingBO = new CompanyFinancingBO();
        financingBO.setFinancingRounds("Pre-IPO");
        String fileName = "C:\\Users\\liyangzhen\\Downloads\\excel\\" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, CompanyFinancingExcel.class).sheet("融资历程").doWrite(() -> {
            List<CompanyFinancingBO> boList = new ArrayList<>();
            for (String companyId : companyIds) {
                financingBO.setCompanyId(companyId);
                boList.add(selectOne(financingBO));
            }
            return boList;
        });
    }

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public void export1(List<String> companyNames) {
        CompanyFinancingBO financingBO = new CompanyFinancingBO();
        financingBO.setFinancingRounds("Pre-IPO");
        String fileName = "C:\\Users\\liyangzhen\\Downloads\\excel\\" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, CompanyFinancingExcel.class).sheet("融资历程").doWrite(() -> {
            List<CompanyFinancingBO> boList = new ArrayList<>();
            for (String companyName : companyNames) {
                CompanyDO companyDO = companyMapper.selectOne(new LambdaQueryWrapper<>(CompanyDO.class)
                        .select(CompanyDO::getCompanyId, CompanyDO::getHonorFlag, CompanyDO::getSsqyFlag)
                        .term("company_name_tag.raw", companyName.trim()));
                if (companyDO == null) {
                    CompanyFinancingBO financingBO1 = new CompanyFinancingBO();
                    financingBO1.setCompanyName(companyName.trim());
                    financingBO1.setSsqyFlag("0");
                    financingBO1.setIpo("0");
                    boList.add(financingBO1);
                    continue;
                }
                financingBO.setCompanyId(companyDO.getCompanyId());
                CompanyFinancingBO financingBO1 = selectOne(financingBO);
                if (financingBO1 == null) {
                    financingBO1 = new CompanyFinancingBO();
                    financingBO1.setCompanyName(companyName.trim());
                    financingBO1.setSsqyFlag("0");
                    financingBO1.setIpo("0");
                } else {
                    financingBO1.setIpo("1");
                }
                financingBO1.setSsqyFlag(StringUtils.isNotBlank(companyDO.getSsqyFlag()) && "1".equals(companyDO.getSsqyFlag()) ? "1" : "0");
                boList.add(financingBO1);
            }
            return boList;
        });
    }

    public List<AggBO> agg(CompanyFinancingBO financingBO) {
        List<AggResponse> list = companyFinancingMapper.agg(new LambdaQueryWrapper<>(CompanyFinancingDO.class)
                .term(CompanyFinancingDO::getCompanyId, financingBO.getCompanyId())
                .agg(CompanyFinancingDO::getFinancingRounds, Aggregation.Kind.Terms));
        return BeanUtil.copyList(list, AggBO::new);
    }
}
