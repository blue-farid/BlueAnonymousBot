package com.blue_farid.blue_anonymous_bot.utils.metric;

import io.micrometer.common.util.StringUtils;
import io.micrometer.core.instrument.*;

import java.util.*;
import java.util.stream.Collectors;

public class TagCounter {

    private final String name;
    private final String[] tagNames;
    private final MeterRegistry registry;
    private final Map<String, Counter> counters = new HashMap<>();

    public TagCounter(String name, MeterRegistry registry, String... tags) {
        this.name = name;
        this.registry = registry;
        this.tagNames = tags;
    }

    public void increment(String... tagValues) {
        this.getCounter(tagValues).increment();
    }

    public void increment(double incrementCount, String... tagValues) {
        this.getCounter(tagValues).increment(incrementCount);
    }

    private Counter getCounter(String... tagValues) {
        List<String> tagValueList = Arrays.stream(tagValues).map(tagValue -> {
            if (Objects.isNull(tagValue) || StringUtils.isBlank(tagValue) || tagValue.isEmpty()) {
                tagValue = "UNKNOWN";
            }
            return tagValue;
        }).collect(Collectors.toList());
        String valuesString = String.join("-", tagValueList);

        Counter counter = counters.get(valuesString);
        if (counter == null) {
            List<Tag> tags = new ArrayList<>(tagNames.length);
            for (int i = 0; i < tagNames.length; i++) {
                tags.add(new ImmutableTag(tagNames[i], tagValueList.get(i)));
            }
            counter = Counter.builder(name).tags(tags).register(registry);
            counters.put(valuesString, counter);
        }
        return counter;
    }

    public List<Meter.Id> getCounterIds() {
        return this.counters.values()
                .stream()
                .map(Meter::getId)
                .collect(Collectors.toList());
    }
}
