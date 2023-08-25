package com.blue_farid.blue_anonymous_bot.utils.metric;

import com.blue_farid.blue_anonymous_bot.annotation.Response;
import com.blue_farid.blue_anonymous_bot.telegram.command.CommandService;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetricUtil {
    private final TagCounter request;



    public MetricUtil(MeterRegistry registry) {
        List<String> tags = new ArrayList<>();
        Arrays.stream(CommandService.class.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Response.class))
                .forEach(m -> tags.add(m.getName()));

        String[] tagsArray = new String[tags.size()];

        for (int i = 0; i < tags.size(); i++)
            tagsArray[i] = tags.get(i);

        this.request = new TagCounter("request", registry, tagsArray);
    }

    public void increment(String tag) {
        request.increment(tag);
    }
}
