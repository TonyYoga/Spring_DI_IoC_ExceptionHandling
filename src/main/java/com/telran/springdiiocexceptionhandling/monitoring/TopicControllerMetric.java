package com.telran.springdiiocexceptionhandling.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;


@Component
public class TopicControllerMetric {

    private final Counter addTopicCounter;
    private final Counter getAllTopicCounter;
    private final Counter removeTopicCounter;

    public TopicControllerMetric(MeterRegistry registry) {
        addTopicCounter = registry.counter("Add Topic Counter");
        getAllTopicCounter = Counter.builder("getAllTopicCounter")
                .description("Get all topic endpoint counter")
                .register(registry);
        removeTopicCounter = Counter.builder("removeTopicCounter")
                .description("Remove topic endpoint counter")
                .register(registry);
    }

    public void handleAddTopic() {
        addTopicCounter.increment();
    }

    public void handleGetAllTopic() {
        getAllTopicCounter.increment();
    }

    public void handleRemoveTopic() {
        removeTopicCounter.increment();
    }
}
