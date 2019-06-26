package com.telran.springdiiocexceptionhandling.monitoring;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CommentControllerMetric {

    private final Counter addCommentCounter;
    private final Counter removeCommentCounter;
    private final Counter updateCommentCounter;

    public CommentControllerMetric(MeterRegistry registry) {
        addCommentCounter = Counter.builder("addCommentCounter")
                .description("Add comment endpoint counter")
                .register(registry);
        removeCommentCounter = Counter.builder("removeCommentCounter")
                .description("Remove comment endpoint counter")
                .register(registry);
        updateCommentCounter = Counter.builder("updateCommentCounter")
                .description("Update comment endpoint counter")
                .register(registry);
    }

    public void handleAddComment() {
        addCommentCounter.increment();
    }

    public void handleRemoveComment() {
        removeCommentCounter.increment();
    }

    public void handleUpdateComment() {
        updateCommentCounter.increment();
    }
}
