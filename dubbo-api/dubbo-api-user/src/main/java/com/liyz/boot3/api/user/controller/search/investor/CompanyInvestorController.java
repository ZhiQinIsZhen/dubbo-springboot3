package com.liyz.boot3.api.user.controller.search.investor;

import com.liyz.boot3.api.user.dto.search.investor.CompanyInvestorDTO;
import com.liyz.boot3.api.user.vo.search.investor.CompanyInvestorVO;
import com.liyz.boot3.common.api.result.PageResult;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.search.bo.investor.CompanyInvestorBO;
import com.liyz.boot3.service.search.query.PageQuery;
import com.liyz.boot3.service.search.remote.investor.RemoteCompanyInvestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:48
 */
@Tag(name = "投资机构")
@RestController
@RequestMapping("/search/investor")
public class CompanyInvestorController {

    @DubboReference
    private RemoteCompanyInvestorService remoteCompanyInvestorService;

    @Anonymous
    @Operation(summary = "根据id查询投资机构信息")
    @GetMapping("/id")
    public Result<CompanyInvestorVO> getById(@RequestParam("id") String id) {
        CompanyInvestorBO investorBO = remoteCompanyInvestorService.getById(id);
        return Result.success(BeanUtil.copyProperties(investorBO, CompanyInvestorVO::new));
    }

    @Anonymous
    @Operation(summary = "分页查询公司")
    @GetMapping("/page")
    public PageResult<CompanyInvestorVO> investorPage(@ParameterObject @Valid CompanyInvestorDTO investorDTO) {
        RemotePage<CompanyInvestorBO> remotePage = remoteCompanyInvestorService.page(BeanUtil.copyProperties(investorDTO, PageQuery::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, CompanyInvestorVO::new)
        );
    }
}
