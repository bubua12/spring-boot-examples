package com.bubua12.boot.kafka.controller;

import com.bubua12.boot.kafka.constant.KafkaConstants;
import jakarta.annotation.Resource;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 15:23
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;


    /**
     * 发送消息：最简单的发送
     * 特点：
     * 1、不关心发送结果
     * 2、适用于日志类、非关键场景
     *
     * @param message 消息
     * @return boolean
     */
    @RequestMapping("/sendSimple")
    public boolean sendSimple(@RequestParam("message") String message) {
        kafkaTemplate.send(KafkaConstants.TEST_TOPIC, message);
        return true;
    }

    /**
     * 同步发送（等待返回结果）
     * 适用于：
     * 1、必须确认消息一定被 Kafka 接收
     * 2、金融、支付、对账等关键业务
     *
     * @param message 消息
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @RequestMapping("/sendSync")
    public void sendSync(@RequestParam("message") String message) throws ExecutionException, InterruptedException {
        // fixme juc
        SendResult<String, Object> sendResult = kafkaTemplate.send(KafkaConstants.TEST_TOPIC, message).get();
        System.out.println("发送结果：" + sendResult.toString());
    }


    /**
     * 异步发送
     * 适用于：
     * 1、高并发
     * 2、不阻塞主线程
     *
     * @param message 消息
     */
    @RequestMapping("/sendAsync")
    public void sendAsync(@RequestParam("message") String message) {
        CompletableFuture<SendResult<String, Object>> feature = kafkaTemplate.send(KafkaConstants.TEST_TOPIC, message);

        feature.whenComplete((result, exception) -> {
            if (exception != null) {
                System.out.println("发送失败，错误消息：" + exception.getMessage());
            }
            System.out.println("发送成功，offset：" + result.getRecordMetadata().offset());
        });

    }


    // *********************************************************************************************

    /**
     * 发送带Key的消息
     *
     * @param key     fixme key是什么、有什么用
     * @param message 消息
     */
    @RequestMapping("/sendWithKey")
    public void sendWithKey(@RequestParam("key") String key, @RequestParam("message") String message) {
        kafkaTemplate.send(KafkaConstants.TEST_TOPIC, key, message);
    }


    /**
     * 发送到指定分区
     *
     * @param partition 分区 fixme 分区是什么、有什么用
     * @param key       key
     * @param message   消息
     */
    @RequestMapping("/sendToPartition")
    public void sendToPartition(@RequestParam("partition") Integer partition,
                                @RequestParam("key") String key,
                                @RequestParam("message") String message) {
        kafkaTemplate.send(KafkaConstants.TEST_TOPIC, partition, key, message);
    }


    /**
     * 发送ProducerRecord fixme ProducerRecord是啥、区别是啥
     *
     * @param key     key
     * @param message 消息
     */
    @RequestMapping("/sendProducerRecord")
    public void sendProducerRecord(@RequestParam("key") String key,
                                   @RequestParam("message") String message) {
        kafkaTemplate.send(new ProducerRecord<>(KafkaConstants.TEST_TOPIC, key, message));
    }
}
