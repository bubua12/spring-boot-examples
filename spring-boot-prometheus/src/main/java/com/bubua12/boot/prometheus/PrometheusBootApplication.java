package com.bubua12.boot.prometheus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * fixme 接口错误率及接口性能指标接入prometheus
 *
 * @author bubua12
 * @since 2025/11/28 16:15
 */
@SpringBootApplication
public class PrometheusBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrometheusBootApplication.class, args);
    }
}
