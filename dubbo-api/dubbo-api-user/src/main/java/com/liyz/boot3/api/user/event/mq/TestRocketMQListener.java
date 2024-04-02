package com.liyz.boot3.api.user.event.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/1 17:50
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "QJD_NC", selectorExpression = "TEST", consumerGroup = "QJD_NC_TEST", consumeThreadMax = 20)
public class TestRocketMQListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("receive msg : {}", s);
    }
}
