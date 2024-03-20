package com.liyz.boot3.service.search.remote.bid;

import com.liyz.boot3.service.search.bo.bid.BidTenderCountBO;

import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/18 14:40
 */
public interface RemoteBidTenderCountService {

    List<BidTenderCountBO> list(BidTenderCountBO query);
}
