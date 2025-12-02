package com.bubua12.boot.prometheus.controller;

import com.bubua12.boot.prometheus.monitor.PrometheusCustomMonitor;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 *
 *
 * @author bubua12
 * @since 2025/11/28 16:19
 */
@RestController
public class TestController {
    @Resource
    private PrometheusCustomMonitor monitor;

    @RequestMapping("/order")
    public String order(@RequestParam(name = "flag", defaultValue = "0") String flag) throws Exception {
        // 统计下单次数
        monitor.getOrderCount().increment();
        if ("1".equals(flag)) {
            throw new Exception("出错啦");
        }
        Random random = new Random();
        int amount = random.nextInt(100);
        // 统计金额
        monitor.getAmountSum().record(amount);

        monitor.getFailCaseNum().set(amount);
        return "下单成功, 金额: " + amount;
    }
}
