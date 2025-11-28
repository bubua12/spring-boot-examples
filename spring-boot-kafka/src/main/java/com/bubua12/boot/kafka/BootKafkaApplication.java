package com.bubua12.boot.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/27 15:18
 */
@SpringBootApplication
@Slf4j
public class BootKafkaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootKafkaApplication.class, args);
        log.info("BootKafkaApplication started successfully... ...O(∩_∩)O");
    }
}
