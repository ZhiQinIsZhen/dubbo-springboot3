package com.liyz.boot3.api.user.controller.search.bid;

import com.liyz.boot3.api.user.dto.search.bid.BidTenderCountDTO;
import com.liyz.boot3.api.user.vo.search.bid.BidTenderCountVO;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.common.service.util.BeanUtil;
import com.liyz.boot3.security.client.annotation.Anonymous;
import com.liyz.boot3.service.search.bo.bid.BidTenderCountBO;
import com.liyz.boot3.service.search.remote.bid.RemoteBidTenderCountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/18 14:53
 */
@Anonymous
@Tag(name = "招投标")
@RestController
@RequestMapping("/search/bid")
public class BidController {

    @DubboReference
    private RemoteBidTenderCountService remoteBidTenderCountService;

    @Operation(summary = "查询列表")
    @GetMapping("/list")
    public Result<List<BidTenderCountVO>> list(BidTenderCountDTO dto) {
        List<BidTenderCountBO> boList = remoteBidTenderCountService.list(BeanUtil.copyProperties(dto, BidTenderCountBO::new));
        return Result.success(BeanUtil.copyList(boList, BidTenderCountVO::new));
    }
}
