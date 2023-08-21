package com.blue_farid.blue_anonymous_bot.utils;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricUtil {
    private final Counter totalSendMessages;

    public MetricUtil(MeterRegistry registry) {
        totalSendMessages = registry.counter("send_messages");
    }

    public void incrementTotalSendMessages() {
        totalSendMessages.increment();
    }
}
