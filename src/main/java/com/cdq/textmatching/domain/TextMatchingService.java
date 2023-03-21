package com.cdq.textmatching.domain;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@AllArgsConstructor
public class TextMatchingService {

    private final TextMatchingTaskRepository textMatchingTaskRepository;
    private final TextMatchingTaskMessagePublisher textMatchingTaskMessagePublisher;

    public TextMatchingTask startTextMatchingTask(String inputText, String pattern) {
        validateNotEmpty(inputText, "Input text");
        validateNotEmpty(inputText, "Pattern");
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess(inputText, pattern);
        textMatchingTaskMessagePublisher.publish(TextMatchingTaskMessage.from(textMatchingTask));
        return textMatchingTaskRepository.save(textMatchingTask);
    }

    private static void validateNotEmpty(String value, String fieldName) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty!");
        }
    }

    public Optional<TextMatchingTaskResult> findTextMatchingTaskResult(UUID id) {
        return textMatchingTaskRepository.findById(id)
                .map(this::asResultWithProgressPercentage);
    }

    private TextMatchingTaskResult asResultWithProgressPercentage(TextMatchingTask textMatchingTask) {
        BigDecimal progressPercentage = calculateProgressPercentage(textMatchingTask);
        return new TextMatchingTaskResult(textMatchingTask, progressPercentage);
    }

    private BigDecimal calculateProgressPercentage(TextMatchingTask textMatchingTask) {
        if (textMatchingTask.getLastCheckedPosition().isEmpty()) {
            return BigDecimal.ZERO;
        }
        // progress percentage = ((last checked position + 1) / input text length) * 100
        return new BigDecimal(textMatchingTask.getLastCheckedPosition().get() + 1)
                .divide(new BigDecimal(textMatchingTask.getInputText().length()), 2, RoundingMode.DOWN)
                .multiply(new BigDecimal(100));
    }

    public List<TextMatchingTaskResult> findTextMatchingTasks() {
        return textMatchingTaskRepository.findAll().stream()
                .map(this::asResultWithProgressPercentage)
                .toList();
    }
}
