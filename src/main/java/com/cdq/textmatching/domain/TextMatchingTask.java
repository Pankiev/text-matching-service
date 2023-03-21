package com.cdq.textmatching.domain;

import lombok.Data;

import java.util.Optional;
import java.util.UUID;

import static com.cdq.textmatching.domain.TextMatchingTaskStatus.*;

@Data
public class TextMatchingTask {

    private final UUID id;
    private final TextMatchingTaskStatus status;
    private final String inputText;
    private final String pattern;
    private final Integer lastCheckedPosition;
    private final TextMatch bestMatch;


    public boolean requiresProcessing() {
        return status.doesIndicateNeedForProcessing();
    }

    public Optional<Integer> getLastCheckedPosition() {
        return Optional.ofNullable(lastCheckedPosition);
    }

    public Optional<TextMatch> getBestMatch() {
        return Optional.ofNullable(bestMatch);
    }

    public static TextMatchingTask newTaskToProcess(String inputText, String pattern) {
        return new TextMatchingTask(UUID.randomUUID(), TO_PROCESS, inputText, pattern, null, null);
    }

    public static TextMatchingTask newTaskToProcess(UUID id, String inputText, String pattern) {
        return new TextMatchingTask(id, TO_PROCESS, inputText, pattern, null, null);
    }

    public TextMatchingTask newPartlyProcessedTask(int lastCheckedPosition, TextMatch currentBestMatch) {
        return new TextMatchingTask(id, PROCESSING, inputText, pattern, lastCheckedPosition, currentBestMatch);
    }

    public TextMatchingTask newCompletedTask(TextMatch bestMatch) {
        return new TextMatchingTask(id, FINISHED, inputText, pattern, inputText.length() - 1, bestMatch);
    }
}
