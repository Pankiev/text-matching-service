package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingTaskResult;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
record TextMatchingTaskResultRestDto(TextMatchingTaskRestDto textMatchingTask, BigDecimal progressPercentage) {
    static TextMatchingTaskResultRestDto of(TextMatchingTaskResult textMatchingTaskResult) {
        return new TextMatchingTaskResultRestDto(
                TextMatchingTaskRestDto.of(textMatchingTaskResult.textMatchingTask()),
                textMatchingTaskResult.progressPercentage()
        );
    }
}
