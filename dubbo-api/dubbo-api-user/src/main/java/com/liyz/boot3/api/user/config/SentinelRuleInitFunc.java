package com.liyz.boot3.api.user.config;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/8/15 21:07
 */
public class SentinelRuleInitFunc implements InitFunc {

    @Override
    public void init() throws Exception {
        FlowRule flowRule = new FlowRule();
        flowRule.setCount(2);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setResource("test");
        List<FlowRule> list = new ArrayList<>();
        list.add(flowRule);

        FlowRuleManager.loadRules(list);
    }
}
