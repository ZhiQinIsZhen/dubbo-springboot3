package com.liyz.boot3.service.search.provider.bid;

import com.liyz.boot3.common.util.JsonMapperUtil;
import com.liyz.boot3.service.search.bo.agg.AggBO;
import com.liyz.boot3.service.search.bo.bid.BidDetailBO;
import com.liyz.boot3.service.search.bo.company.CompanyBO;
import com.liyz.boot3.service.search.query.agg.AggQuery;
import com.liyz.boot3.service.search.query.bid.BidDetailPageQuery;
import com.liyz.boot3.service.search.query.company.CompanyPageQuery;
import com.liyz.boot3.service.search.remote.bid.RemoteBidDetailService;
import com.liyz.boot3.service.search.remote.company.RemoteCompanyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 14:55
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RemoteBidDetailServiceImplTest {

    @Resource
    private RemoteBidDetailService remoteBidDetailService;

    @Test
    public void testSearch() {
        BidDetailPageQuery query = new BidDetailPageQuery();
        query.setCompanyId("d6189b072c60ca0d668e05c006c45db6");
        BidDetailBO bo = remoteBidDetailService.search(query);
        log.info("value : {}", JsonMapperUtil.toJSONString(bo));
    }

    @Test
    public void testId() {
        BidDetailBO bo = remoteBidDetailService.getById("4a5a5492d9397bf514c313b081a14d75");
        log.info("value : {}", JsonMapperUtil.toJSONString(bo));
    }

    @Test
    public void testAgg() {
        BidDetailPageQuery query = new BidDetailPageQuery();
        query.setCompanyId("6bfdcc14fb83b5dd926fd5308f2ce699");
        AggQuery aggQuery = new AggQuery();
        aggQuery.setField("notice_type_sub");
        aggQuery.setOrder("_count");
        aggQuery.setDesc(true);
        List<AggBO> list = remoteBidDetailService.agg(query, aggQuery);
        log.info("value : {}", JsonMapperUtil.toJSONString(list));
    }
}