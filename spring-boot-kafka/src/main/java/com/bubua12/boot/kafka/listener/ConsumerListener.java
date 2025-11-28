package com.bubua12.boot.kafka.listener;

import com.bubua12.boot.kafka.constant.KafkaConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 15:25
 */
@Component
public class ConsumerListener {

    @KafkaListener(topics = KafkaConstants.TEST_TOPIC, groupId = KafkaConstants.TEST_CONSUMER_GROUP)
    public void onMessage(String message) {
        // eg 插入数据库
        System.out.println("监听到了消息：" + message);
    }

}
