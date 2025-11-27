package com.bubua12.boot.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.StopWatch;

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
        CompletableFuture[] futures = new CompletableFuture[1000000];
        for (int i = 0; i < 1000000; i++) {
            CompletableFuture send = kafkaTemplate.send("order5", "order.create." + i, "订单创建了：" + i);
            futures[i] = send;
        }
        CompletableFuture.allOf(futures).join();
        watch.stop();
        System.out.println("总耗时：" + watch.getTotalTimeMillis() + " ms");
    }
}
