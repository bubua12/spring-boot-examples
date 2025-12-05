package com.bubua12.boot.kafka.listener;

import com.bubua12.boot.kafka.constant.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 15:25
 */
@Component
public class ConsumerListener {

    /**
     * fixme 这里刚启动并不会消费之前剩余的消息
     *
     * @param message message
     */
    @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_CONSUMER_GROUP)
    public void onMessage(String message) {
        // eg 插入数据库
//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        System.out.println("监听到了消息：" + message);
    }

    @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_CONSUMER_GROUP)
    public void onMessage1(String message) {
        // eg 插入数据库
//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        System.out.println("监听到了消息：" + message);
    }

    @KafkaListener(topics = KafkaConstants.ORDER_TOPIC, groupId = KafkaConstants.ORDER_CONSUMER_GROUP_01)
    public void groupConsumer01(String message) {
        try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        System.out.println("[ORDER-01]监听到了消息：" + message);
    }

    @KafkaListener(topics = KafkaConstants.ORDER_TOPIC, groupId = KafkaConstants.ORDER_CONSUMER_GROUP_02)
    public void groupConsumer02(String message) {
        try { TimeUnit.MILLISECONDS.sleep(600); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        System.out.println("[ORDER-02]监听到了消息：" + message);
    }


    @KafkaListener(topics = KafkaConstants.STOCK_TOPIC, groupId = KafkaConstants.STOCK_CONSUMER_GROUP)
    public void stockConsumer(String message) {
        try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
        System.out.println("[STOCK]监听到了消息：" + message);
    }

}
