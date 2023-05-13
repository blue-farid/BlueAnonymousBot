package com.blue_farid.blue_anonymous_bot.utils;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricUtil {
    private final Counter totalRequests;

    public MetricUtil(MeterRegistry registry) {
        totalRequests = registry.counter("total_requests");
    }

    public void incrementTotalRequests() {
        totalRequests.increment();
    }
}
