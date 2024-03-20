package com.liyz.boot3.service.search.provider.bid;

import com.liyz.boot3.service.search.bo.bid.BidTenderCountBO;
import com.liyz.boot3.service.search.remote.bid.RemoteBidTenderCountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/3/18 14:41
 */
@Slf4j
@DubboService
public class RemoteBidTenderCountServiceImpl implements RemoteBidTenderCountService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<BidTenderCountBO> list(BidTenderCountBO query) {
        Criteria criteria = new Criteria();
        criteria.and("partyCount").gte(3);
        Aggregation totalCountAgg = Aggregation.newAggregation(
                Aggregation.match(buildCriteria(query)),
                Aggregation.group("tender_id", "party_name")
                        .first("tender_id").as("tenderId")
                        .last("party_name").as("partyName")
                        .sum("party_count").as("partyCount"),
                Aggregation.match(criteria),
                Aggregation.count().as("total")
        );
        AggregationResults<Map> mapAgg = mongoTemplate.aggregate(totalCountAgg, "ads_company_bid_tender_count", Map.class);
        long totalCount = ((Integer) mapAgg.getMappedResults().get(0).get("total")).longValue();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(buildCriteria(query)),
                Aggregation.group("tender_id", "party_name")
                        .first("tender_id").as("tenderId")
                        .last("party_name").as("partyName")
                        .sum("party_count").as("partyCount"),
                Aggregation.sort(Sort.Direction.DESC, "partyCount", "partyName"),
                Aggregation.skip(0),
                Aggregation.limit(10),
                Aggregation.match(criteria),
                Aggregation.project(BidTenderCountBO.class)
        );
        AggregationResults<BidTenderCountBO> aggregate = mongoTemplate.aggregate(aggregation,
                "ads_company_bid_tender_count", BidTenderCountBO.class);
        List<BidTenderCountBO> mappedResults = aggregate.getMappedResults();
        return mappedResults.stream().toList();
    }

    private Criteria buildCriteria(BidTenderCountBO query) {
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(query.getTenderId())) {
            criteria.and("tender_id").is(query.getTenderId());
        }
        if (StringUtils.isNotBlank(query.getPartyName())) {
            criteria.and("party_name").is(query.getPartyName());
        }
        criteria.and("use_flag").is(0);
        if (Objects.nonNull(query.getType())) {
            if (query.getType() == 1) {
                criteria.and("party_type").is(2);
            } else if (query.getType() == 2) {
                criteria.and("party_type").in(2, 4);
            } else if (query.getType() == 4) {
                criteria.and("party_type").is(3);
            }
        }
        return criteria;
    }
}
