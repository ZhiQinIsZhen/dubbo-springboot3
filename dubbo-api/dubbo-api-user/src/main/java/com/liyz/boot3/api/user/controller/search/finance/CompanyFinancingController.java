package com.liyz.boot3.api.user.controller.search.finance;

import com.liyz.boot3.api.user.dto.search.finance.CompanyFinancingDTO;
import com.liyz.boot3.api.user.vo.search.finance.CompanyFinancingVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.search.bo.company.CompanyFinancingBO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyFinancingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/1/8 13:48
 */
@Tag(name = "企业融资")
@RestController
@RequestMapping("/search/financing")
public class CompanyFinancingController {

    @DubboReference
    private RemoteCompanyFinancingService remoteCompanyFinancingService;

    @Anonymous
    @Operation(summary = "根据id查询投资机构信息")
    @GetMapping("/one")
    public Result<CompanyFinancingVO> getOne(CompanyFinancingDTO dto) {
        CompanyFinancingBO financingBO = remoteCompanyFinancingService.selectOne(BeanUtil.copyProperties(dto, CompanyFinancingBO::new));
        return Result.success(BeanUtil.copyProperties(financingBO, CompanyFinancingVO::new));
    }
}
