package com.liyz.boot3.api.user.controller.search.company;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.liyz.boot3.api.user.dto.search.company.CompanyDTO;
import com.liyz.boot3.api.user.event.SearchEvent;
import com.liyz.boot3.api.user.vo.search.company.CompanyVO;
import com.liyz.boot3.common.api.result.PageResult;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.remote.page.RemotePage;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/15 10:17
 */
@ApiSort(2)
@Tag(name = "搜索")
@RestController
@RequestMapping("/search/company")
public class CompanyController {

    @Resource
    private ApplicationContext applicationContext;
    @DubboReference
    private RemoteCompanyService remoteCompanyService;

    @Operation(summary = "分页查询公司")
    @GetMapping("/page")
    public PageResult<CompanyVO> companyPage(@ParameterObject @Valid CompanyDTO companyDTO) {
        applicationContext.publishEvent(new SearchEvent(this));
        RemotePage<CompanyBO> remotePage = remoteCompanyService.searchPage(BeanUtil.copyProperties(companyDTO, CompanyPageQuery::new));
        return PageResult.success(BeanUtil.copyRemotePage(remotePage, CompanyVO::new, (s, t) -> {
            if (Objects.nonNull(s.getEstablishmentTime())) {
                    t.setEstablishmentTime(new Date(s.getEstablishmentTime() * 1000));
                }
            })
        );
    }

    @Anonymous
    @Operation(summary = "根据id查询公司信息")
    @GetMapping("/id")
    public Result<CompanyVO> companyById(@RequestParam("id") String id) {
        applicationContext.publishEvent(new SearchEvent(this));
        CompanyBO companyBO = remoteCompanyService.getById(id);
        return Result.success(BeanUtil.copyProperties(companyBO, CompanyVO::new, (s, t) -> {
            if (Objects.nonNull(s.getEstablishmentTime())) {
                t.setEstablishmentTime(new Date(s.getEstablishmentTime() * 1000));
            }
        }));
    }

    @Anonymous
    @Operation(summary = "根据id列表查询公司信息列表")
    @GetMapping("/ids")
    public Result<List<CompanyVO>> companyByIds(@RequestParam("ids") List<String> ids) {
        applicationContext.publishEvent(new SearchEvent(this));
        List<CompanyBO> boList = remoteCompanyService.getByIds(ids);
        return Result.success(BeanUtil.copyList(boList, CompanyVO::new, (s, t) -> {
            if (Objects.nonNull(s.getEstablishmentTime())) {
                t.setEstablishmentTime(new Date(s.getEstablishmentTime() * 1000));
            }
        }));
    }

    @Anonymous
    @Operation(summary = "查询公司信息")
    @GetMapping("/selectOne")
    public Result<CompanyVO> selectOne(@RequestParam("companyName") String companyName) {
        CompanyBO bo = new CompanyBO();
        bo.setCompanyNameTag(companyName);
        CompanyBO companyBO = remoteCompanyService.selectOne(bo);
        return Result.success(BeanUtil.copyProperties(companyBO, CompanyVO::new, (s, t) -> {
            if (Objects.nonNull(s.getEstablishmentTime())) {
                t.setEstablishmentTime(new Date(s.getEstablishmentTime() * 1000));
            }
        }));
    }
}
