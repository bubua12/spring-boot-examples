package com.bubua12.boot.prometheus.monitor;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * fixme jakarta & javax
 *
 * @author bubua12
 * @since 2025/11/28 16:17
 */
@Component
public class PrometheusCustomMonitor {

    private Counter requestErrorCount;
    private Counter orderCount;
    private DistributionSummary amountSum;
    private AtomicInteger failCaseNum;

    private final MeterRegistry registry;

    @Autowired
    public PrometheusCustomMonitor(MeterRegistry registry) {
        this.registry = registry;
    }

    @PostConstruct
    private void init() {
        requestErrorCount = registry.counter("requests_error_total", "status", "error");
        orderCount = registry.counter("order_request_count", "order", "test-svc");
        amountSum = registry.summary("order_amount_sum", "orderAmount", "test-svc");
        failCaseNum = registry.gauge("fail_case_num", new AtomicInteger(0));
    }

    public Counter getRequestErrorCount() {
        return requestErrorCount;
    }

    public Counter getOrderCount() {
        return orderCount;
    }

    public DistributionSummary getAmountSum() {
        return amountSum;
    }

    public AtomicInteger getFailCaseNum() {
        return failCaseNum;
    }
}
