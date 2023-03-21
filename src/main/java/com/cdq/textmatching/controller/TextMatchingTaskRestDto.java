package com.cdq.textmatching.controller;

import com.cdq.textmatching.domain.TextMatchingTask;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
record TextMatchingTaskRestDto(UUID id, String status, String inputText, String pattern, TextMatchRestDto bestMatch) {

    static TextMatchingTaskRestDto of(TextMatchingTask textMatchingTask) {
        return new TextMatchingTaskRestDto(
                textMatchingTask.getId(),
                textMatchingTask.getStatus().name(),
                textMatchingTask.getInputText(),
                textMatchingTask.getPattern(),
                textMatchingTask.getBestMatch().map(TextMatchRestDto::of).orElse(null)
        );
    }
}

