package com.liyz.boot3.api.admin.controller.search;

import com.liyz.boot3.api.admin.dto.search.company.CompanyDTO;
import com.liyz.boot3.api.admin.vo.search.company.CompanyVO;
import com.liyz.boot3.common.api.result.PageResult;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 10:17
 */
@Tag(name = "搜索")
@RestController
@RequestMapping("/search")
public class SearchController {

    @DubboReference
    private RemoteCompanyService remoteCompanyService;

    @Operation(summary = "分页查询公司")
    @GetMapping("/company/page")
    public PageResult<CompanyVO> companyPage(@ParameterObject @Valid CompanyDTO companyDTO) {
        RemotePage<CompanyBO> remotePage = remoteCompanyService.searchPage(BeanUtil.copyProperties(companyDTO, CompanyPageQuery::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, CompanyVO::new, (s, t) -> {
            if (Objects.nonNull(s.getEstablishmentTime())) {
                    t.setEstablishmentTime(new Date(s.getEstablishmentTime() * 1000));
                }
            })
        );
    }
}
