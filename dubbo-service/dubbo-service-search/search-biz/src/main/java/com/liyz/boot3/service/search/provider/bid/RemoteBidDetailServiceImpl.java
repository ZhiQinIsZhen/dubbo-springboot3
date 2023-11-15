package com.liyz.boot3.service.search.provider.bid;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.liyz.boot3.service.search.bo.bid.BidDetailBO;
import com.liyz.boot3.service.search.constant.SearchType;
import com.liyz.boot3.service.search.query.bid.BidDetailPageQuery;
import com.liyz.boot3.service.search.remote.bid.RemoteBidDetailService;
import com.liyz.boot3.service.search.service.SearchServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 14:54
 */
@DubboService
public class RemoteBidDetailServiceImpl extends SearchServiceImpl<BidDetailBO, BidDetailPageQuery> implements RemoteBidDetailService {

    @Override
    protected void buildCustomerQuery(BoolQuery.Builder boolBuild, BidDetailPageQuery bidDetailPageQuery) {
        if (StringUtils.isNotBlank(bidDetailPageQuery.getCompanyId())) {
            boolBuild.must(m -> m.nested(n -> n.path("party").query(nq -> nq.bool(nbq -> nbq.must(nbmq -> nbmq.term(nbmtq -> nbmtq.field("party.company_id").value(bidDetailPageQuery.getCompanyId())))))
                    )
            );
        }
    }

    @Override
    protected SearchType getSearchType() {
        return SearchType.BID_DETAIL;
    }
}
