package com.bubua12.boot.rocketmq.listener;

import com.bubua12.boot.rocketmq.entiry.Person;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 15:53
 */
@RocketMQMessageListener(consumerGroup = "rocketmq-group", topic = "person")
public class PersonMqListener implements RocketMQListener<Person> {

    @Override
    public void onMessage(Person person) {
        System.out.println("接收到消息，开始消费：" + person);
    }
}
