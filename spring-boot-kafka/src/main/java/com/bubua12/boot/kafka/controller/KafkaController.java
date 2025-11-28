package com.bubua12.boot.kafka.controller;

import com.bubua12.boot.kafka.constant.KafkaConstants;
import jakarta.annotation.Resource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 发送消息
     *
     * @param message 消息
     * @return boolean
     */
    @RequestMapping("/send")
    public boolean sendMessage(@RequestParam("message") String message) {
        kafkaTemplate.send(KafkaConstants.TEST_TOPIC, message);
        return true;
    }


}
