package com.cdq.textmatching.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record TextMatchingTask(UUID id, TextMatchingTaskStatus status, String inputText, String pattern, BigDecimal taskProgress) {

    public static TextMatchingTask newTaskToProcess(String inputText, String pattern) {
        return new TextMatchingTask(UUID.randomUUID(), TextMatchingTaskStatus.TO_PROCESS, inputText, pattern, BigDecimal.ZERO);
    }
}
