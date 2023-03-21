package com.cdq.textmatching.infrastructure.messaging;

import com.cdq.textmatching.domain.TextMatchingTaskMessage;

import java.io.Serializable;
import java.util.UUID;

record RabbitMqTextMatchingTaskMessage(UUID id, String inputText, String pattern) implements Serializable {

    static RabbitMqTextMatchingTaskMessage of(TextMatchingTaskMessage textMatchingTaskMessage) {
        return new RabbitMqTextMatchingTaskMessage(textMatchingTaskMessage.id(), textMatchingTaskMessage.inputText(),
                textMatchingTaskMessage.pattern());
    }

    TextMatchingTaskMessage toTextMatchingTaskMessage() {
        return new TextMatchingTaskMessage(id, inputText, pattern);
    }
}
