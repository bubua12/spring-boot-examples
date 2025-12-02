package com.bubua12.boot.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 16:10
 */
@SpringBootApplication
@Slf4j
public class BootRabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootRabbitMQApplication.class, args);
        log.info("=== BootRabbitMQApplication Started ===");
    }
}
