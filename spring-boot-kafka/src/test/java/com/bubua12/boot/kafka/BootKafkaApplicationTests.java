package com.bubua12.boot.kafka;

import com.bubua12.boot.kafka.constant.KafkaConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/27 18:22
 */
@SpringBootTest
public class BootKafkaApplicationTests {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 简单的消息发送
     */
    @Test
    void contextLoads() {
        kafkaTemplate.send("test", "hello");
    }

    /**
     * 批量消息测试
     * fixme juc.CompletableFuture
     */
    @Test
    void testSend() {
        StopWatch watch = new StopWatch();
        watch.start();
        CompletableFuture[] futures = new CompletableFuture[8988];
        for (int i = 0; i < 8988; i++) {
            CompletableFuture<SendResult<String, String>> send = kafkaTemplate.send("test-topic", "订单创建了：" + i);
            futures[i] = send;
        }
        CompletableFuture.allOf(futures).join();
        watch.stop();
        System.out.println("总耗时：" + watch.getTotalTimeMillis() + " ms");
    }

    @Test
    void testLag() {
        List<String> topicList = Arrays.asList(KafkaConstants.ORDER_TOPIC, KafkaConstants.PRODUCT_TOPIC, KafkaConstants.STOCK_TOPIC);

        topicList.forEach(topic -> {
            for (int i = 0; i < 198722; i++) {
                kafkaTemplate.send(topic, "这是一个消息：" + topic + i);
            }
        });
    }
}
