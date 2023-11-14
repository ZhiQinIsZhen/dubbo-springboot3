package com.liyz.boot3.service.search.provider.bid;

import com.liyz.boot3.service.search.bo.bid.BidDetailBO;
import com.liyz.boot3.service.search.constant.SearchType;
import com.liyz.boot3.service.search.query.bid.BidDetailPageQuery;
import com.liyz.boot3.service.search.remote.bid.RemoteBidDetailService;
import com.liyz.boot3.service.search.service.SearchServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/11/14 14:54
 */
@Service
public class RemoteBidDetailServiceImpl extends SearchServiceImpl<BidDetailBO, BidDetailPageQuery> implements RemoteBidDetailService {

    @Override
    protected SearchType getSearchType() {
        return SearchType.BID_DETAIL;
    }
}
