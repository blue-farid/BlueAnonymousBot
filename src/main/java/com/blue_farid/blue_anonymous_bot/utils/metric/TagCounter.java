package com.blue_farid.blue_anonymous_bot.utils.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TagCounter {
    private final Map<String, Counter> counters = new HashMap<>();

    public TagCounter(String name, MeterRegistry registry, String... tags) {
        Arrays.stream(tags).forEach(t ->
                counters.put(t, Counter.builder(name + "." + t).register(registry)));
    }

    public void increment(String tag) {
        this.counters.get(tag).increment();
    }
}
