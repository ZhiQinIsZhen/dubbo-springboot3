package com.liyz.boot3.api.user.controller.test;

import com.liyz.boot3.api.user.utils.RocketMQUtil;
import com.liyz.boot3.common.api.result.Result;
import com.liyz.boot3.security.client.annotation.Anonymous;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/1 15:05
 */
@Slf4j
@Anonymous
@Tag(name = "MQ测试")
@RestController
@RequestMapping("/test/mq")
public class TestMqController implements ApplicationListener<ContextRefreshedEvent> {

    /*@Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Operation(summary = "kafka发送消息")
    @GetMapping("/kafka/send")
    public Result<Boolean> kafkaSend() {
        kafkaTemplate.send("nc-beta", String.valueOf(System.currentTimeMillis()));
        return Result.success(Boolean.TRUE);
    }

    @KafkaListener(topics = {"nc-beta"})
    public void consumerKafka(ConsumerRecord<String, String> record) {
        log.info("topic:{}, partition:{}, msg:{}", record.topic(), record.partition(), record.value());
    }*/

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Operation(summary = "rocketmq发送消息")
    @GetMapping("/rocketmq/send")
    public Result<Boolean> rocketmqSend() {
        String businessKey = String.valueOf(System.currentTimeMillis());
        SendResult sendResult = RocketMQUtil.syncSend("QJD_NC", "TEST", businessKey, businessKey);
        log.info("send test record status : {}", sendResult.getSendStatus().name());
        return Result.success(Boolean.TRUE);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        RocketMQUtil.setRocketMQTemplate(rocketMQTemplate);
    }
}
