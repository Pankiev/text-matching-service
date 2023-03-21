package com.cdq.textmatching.domain;

public interface TextMatchingTaskMessagePublisher {

    void publish(TextMatchingTaskMessage textMatchingTask);
}
