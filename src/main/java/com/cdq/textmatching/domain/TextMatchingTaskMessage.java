package com.cdq.textmatching.domain;

import java.util.UUID;

public record TextMatchingTaskMessage(UUID id, String inputText, String pattern) {

    static TextMatchingTaskMessage from(TextMatchingTask textMatchingTask) {
        return new TextMatchingTaskMessage(textMatchingTask.getId(), textMatchingTask.getInputText(), textMatchingTask.getPattern());
    }
}
