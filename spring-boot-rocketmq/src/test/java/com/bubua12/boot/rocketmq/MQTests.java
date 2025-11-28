package com.bubua12.boot.rocketmq;

import com.bubua12.boot.rocketmq.entiry.Person;
import com.bubua12.boot.rocketmq.utils.RocketMqHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;

/**
 * fixme SpringBoot Test
 *
 * @author bubua12
 * @since 2025/11/28 15:58
 */
@SpringBootTest
public class MQTests {
    @Autowired
    private RocketMqHelper rocketMqHelper;

    @Test
    public void testProducer() throws InterruptedException {
        for (int i = 0; i < 1000000; i++) {
            Person person = new Person();
            person.setName("jack");
            person.setAge(25);
            rocketMqHelper.asyncSend("PERSON_ADD", MessageBuilder.withPayload(person).build());
            Thread.sleep(1000);
        }
    }
}