package com.liyz.boot3.api.user.controller.search.finance;

import com.liyz.boot3.api.user.dto.search.finance.CompanyFinancingDTO;
import com.liyz.boot3.api.user.dto.search.finance.ExportDTO;
import com.liyz.boot3.api.user.vo.search.AggVO;
import com.liyz.boot3.api.user.vo.search.finance.CompanyFinancingVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.search.bo.agg.AggBO;
import com.liyz.boot3.service.search.bo.company.CompanyFinancingBO;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyFinancingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Operation(summary = "查询投资机构单条数据")
    @GetMapping("/one")
    public Result<CompanyFinancingVO> getOne(CompanyFinancingDTO dto) {
        CompanyFinancingBO financingBO = remoteCompanyFinancingService.selectOne(BeanUtil.copyProperties(dto, CompanyFinancingBO::new));
        return Result.success(BeanUtil.copyProperties(financingBO, CompanyFinancingVO::new));
    }

    @Anonymous
    @Operation(summary = "投资机构导出")
    @GetMapping("/export")
    public Result export(@RequestParam("ids")List<String> ids) {
        remoteCompanyFinancingService.export(ids);
        return Result.success();
    }

    @Anonymous
    @Operation(summary = "投资机构导出")
    @PostMapping("/export1")
    public Result export1(@RequestBody ExportDTO exportDTO) {
        remoteCompanyFinancingService.export1(exportDTO.getCompanyIds());
        return Result.success();
    }



    @Anonymous
    @Operation(summary = "聚合")
    @GetMapping("/agg")
    public Result<List<AggVO>> agg(CompanyFinancingDTO dto) {
        List<AggBO> boList = remoteCompanyFinancingService.agg(BeanUtil.copyProperties(dto, CompanyFinancingBO::new));
        return Result.success(BeanUtil.copyList(boList, AggVO::new));
    }
}
