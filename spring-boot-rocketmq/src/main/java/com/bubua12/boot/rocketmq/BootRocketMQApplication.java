package com.bubua12.boot.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/27 19:29
 */
@SpringBootApplication
@Slf4j
public class BootRocketMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootRocketMQApplication.class, args);
        log.info("BootRocketMQApplication started successfully... ...O(∩_∩)O");
    }
}
