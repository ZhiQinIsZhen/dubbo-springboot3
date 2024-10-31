package com.liyz.boot3.api.test.util;

import lombok.Setter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.concurrent.Callable;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2024/4/1 17:28
 */
@Slf4j
@UtilityClass
public class RocketMQUtil {

    @Setter
    private static RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     *     同步发送是指消息发送方发出数据后，会阻塞直到MQ服务方发回响应消息。
     *     适合场景：重要通知消息
     * @param topic  表示消息的第一级类型，比如一个电商系统的消息可以分为：交易消息、物流消息...... 一条消息必须有一个Topic
     * @param tag   Tags是Topic下的次级消息类型/二级类型，可以在同一个Topic下基于Tags进行消息过滤。
     * @param key   消息的关键KEY，可以用于消息查询，或者当有序消息时，用于分片的KEY，最好保证唯一
     * @param data  消息，消息队列中信息传递的载体。
     * @param <T>
     */
    public static  <T> SendResult syncSend(String topic, String tag, String key, T data) {
        SendResult sendResult = rocketMQTemplate.syncSend(buildDestination(topic,tag), buildMessage(key,data));
        log.debug("syncSend topic:{},tag:{},sendResult:{}", topic, tag,sendResult);
        return sendResult;
    }

    /**
     * 发送有序消息
     *     消息有序指的是一类消息消费时，能按照发送的顺序来消费。
     * @param topic  表示消息的第一级类型，比如一个电商系统的消息可以分为：交易消息、物流消息...... 一条消息必须有一个Topic
     * @param tag   Tags是Topic下的次级消息类型/二级类型，可以在同一个Topic下基于Tags进行消息过滤。
     * @param data  消息，消息队列中信息传递的载体。
     * @param key   对于指定的一个 Topic，所有消息根据 sharding key 进行区块分区。 同一个分区内的消息按照严格的 FIFO 顺序进行发布和消费。 Sharding key 是顺序消息中用来区分不同分区的关键字段
     * @param <T>
     */
    public <T> SendResult syncSendOrderly(String topic, String tag,String key,T data) {
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(buildDestination(topic,tag), buildMessage(key,data), key);
        log.debug("syncSendOrderly topic:{},tag:{},sendResult:{}", topic, tag,sendResult);
        return sendResult;
    }

    /**
     *
     * @param topic  表示消息的第一级类型，比如一个电商系统的消息可以分为：交易消息、物流消息...... 一条消息必须有一个Topic
     * @param tag    Tags是Topic下的次级消息类型/二级类型，可以在同一个Topic下基于Tags进行消息过滤。
     * @param key    消息业务标识,例如订单ID等
     * @param data   消息，消息队列中信息传递的载体。
     * @param callable   业务处理逻辑
     * @param <T>
     * @param <R>
     * @return
     */
    public <T, R> R sendMessageInTransaction(String topic, String tag, String key, T data, Callable<R> callable) {
        TxMessage<T, R> txMessage = new TxMessage(key, data, callable);
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(buildDestination(topic,tag),
                buildMessage(key,data), txMessage);
        log.debug("sendMessageInTransaction topic:{},tag:{},transactionSendResult:{}", topic, tag,transactionSendResult);
        return txMessage.getResult();
    }

    /**
     * 异步发送
     *   异步发送是指发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式。MQ 的异步发送，需要用户实现异步发送回调接口（SendCallback）
     * @param topic  表示消息的第一级类型，比如一个电商系统的消息可以分为：交易消息、物流消息...... 一条消息必须有一个Topic
     * @param tag   Tags是Topic下的次级消息类型/二级类型，可以在同一个Topic下基于Tags进行消息过滤。
     * @param data  消息，消息队列中信息传递的载体。
     * @param sendCallback 回调函数
     * @param <T>
     */
    public <T> void asyncSend(String topic, String tag,String key, T data, SendCallback sendCallback) {
        log.debug("asyncSend topic:{},tag:{}", topic, tag);
        rocketMQTemplate.asyncSend(buildDestination(topic,tag), buildMessage(key,data), sendCallback);
    }

    /**
     * 单向发送
     *    单向（Oneway）发送特点为只负责发送消息，合适场景：日志等
     * @param topic
     * @param tag
     * @param data
     * @param <T>
     */
    public <T> void sendOneWay(String topic, String tag,String key, T data) {
        log.debug("sendOneWay topic:{},tag:{}", topic, tag);
        rocketMQTemplate.sendOneWay(buildDestination(topic,tag), buildMessage(key,data));
    }

    /**
     *
     * @param topic  表示消息的第一级类型，比如一个电商系统的消息可以分为：交易消息、物流消息...... 一条消息必须有一个Topic
     * @param tag   Tags是Topic下的次级消息类型/二级类型，可以在同一个Topic下基于Tags进行消息过滤。
     * @param data  消息，消息队列中信息传递的载体。
     * delayLevel：延迟级别，开源版只有固定的延迟级别，不支持任意时间延迟
     *     1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     * @param <T>
     */
    public <T> SendResult sendDelayLevel(String topic, String tag, String key,T data,int delayLevel) {
        SendResult sendResult = rocketMQTemplate.syncSend(buildDestination(topic,tag),buildMessage(key,data), 3000, delayLevel);
        log.debug("sendDelayLevel topic:{},tag:{},sendResult:{}", topic, tag,sendResult);
        return sendResult;
    }

    private static String buildDestination(String topic, String tag){
        return topic + ":" + tag;
    }

    private static <T> Message<T> buildMessage(String key, T data){
        return MessageBuilder.withPayload(data).setHeader(RocketMQHeaders.KEYS, key).build();
    }
}
