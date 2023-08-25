package com.blue_farid.blue_anonymous_bot.utils.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricUtil {
    private final TagCounter request;


    public MetricUtil(MeterRegistry registry) {
        this.request = new TagCounter("request", registry, "type");
    }

    public void increment(String tag) {
        request.increment(tag);
    }
}
