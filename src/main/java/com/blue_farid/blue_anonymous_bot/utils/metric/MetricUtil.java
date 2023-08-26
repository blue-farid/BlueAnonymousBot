package com.blue_farid.blue_anonymous_bot.utils.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class MetricUtil {
    private final TagCounter request;
    private final TagCounter job;


    public MetricUtil(MeterRegistry registry) {
        this.request = new TagCounter("request", registry, "type");
        this.job = new TagCounter("job", registry, "job_name", "type");
    }

    public void incrementRequest(String tag) {
        request.increment(tag);
    }

    public void incrementJob(String... tag) {
        job.increment(tag);
    }
}
