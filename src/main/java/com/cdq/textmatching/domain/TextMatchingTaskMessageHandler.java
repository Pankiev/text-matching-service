package com.cdq.textmatching.domain;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.IntStream;

@AllArgsConstructor
@Component
@Log4j2
public class TextMatchingTaskMessageHandler {

    private final TextMatchingTaskRepository textMatchingTaskRepository;

    public void handle(TextMatchingTaskMessage textMatchingTaskMessage) {
        TextMatchingTask taskToProcess = provideTaskToProcess(textMatchingTaskMessage);
        process(taskToProcess);
    }

    private TextMatchingTask provideTaskToProcess(TextMatchingTaskMessage textMatchingTaskMessage) {
        // Fetch task that could be partly computed if process crashed or hanged during computation
        // Otherwise we would have to compute it from the beginning
        Optional<TextMatchingTask> taskOptional = textMatchingTaskRepository.findById(textMatchingTaskMessage.id());
        // It could happen that task is not persisted yet in storage because of network partition between database
        // nodes, race condition or replication delay. In that case we safely assume that the task has not started yet.
        return taskOptional.orElseGet(() -> TextMatchingTask.newTaskToProcess(
                textMatchingTaskMessage.id(),
                textMatchingTaskMessage.inputText(),
                textMatchingTaskMessage.pattern()
        ));
    }

    private void process(TextMatchingTask taskToProcess) {
        log.info("Processing task id {}", taskToProcess.getId());
        TextMatchingTask partiallyComputedTask = processFirstPositionIfRequired(taskToProcess);
        TextMatchingTask finishedTask = processPartiallyComputedTask(partiallyComputedTask);
        log.info("Processing task id {} finished. Best match: {}", finishedTask.getId(), finishedTask.getBestMatch().orElseThrow());
    }

    private TextMatchingTask processPartiallyComputedTask(TextMatchingTask partiallyComputedTask) {
        TextMatchingTask currentTask = partiallyComputedTask;
        while (currentTask.requiresProcessing()) {
            currentTask = processNextInputTextPosition(currentTask);
            log.info("Processed {} out of {} positions",
                    currentTask.getLastCheckedPosition().orElseThrow() + 1,
                    currentTask.getInputText().length());
        }
        return currentTask;
    }

    private TextMatchingTask processNextInputTextPosition(TextMatchingTask partiallyComputedTask) {
        int positionToCompute = partiallyComputedTask.getLastCheckedPosition().orElseThrow(IllegalArgumentException::new) + 1;
        TextMatch currentBestMatch = partiallyComputedTask.getBestMatch().orElseThrow(IllegalArgumentException::new);
        TextMatch newMatch = calculateTextMatch(positionToCompute, partiallyComputedTask.getInputText(), partiallyComputedTask.getPattern());
        TextMatch newBestMatch = newMatch.matchesBetterThan(currentBestMatch) ? newMatch : currentBestMatch;
        log.info("Text match for task with id {} calculated: {}", partiallyComputedTask.getId(), newMatch);
        return updateProgress(partiallyComputedTask, positionToCompute, newBestMatch);
    }

    private TextMatchingTask processFirstPositionIfRequired(TextMatchingTask taskToProcess) {
        Optional<TextMatch> currentBestMatch = taskToProcess.getBestMatch();
        if (currentBestMatch.isEmpty()) {
            TextMatch firstPositionTextMatch = calculateTextMatch(0, taskToProcess.getInputText(), taskToProcess.getPattern());
            log.info("Text match for task with id {} calculated: {}", taskToProcess.getId(), firstPositionTextMatch);
            return updateProgress(taskToProcess, 0, firstPositionTextMatch);
        }
        return taskToProcess;
    }

    private TextMatchingTask updateProgress(TextMatchingTask processedTask, int computedPosition, TextMatch currentBestMatch) {
        TextMatchingTask computedPositionAppliedTask = applyComputedPosition(processedTask, computedPosition, currentBestMatch);
        return textMatchingTaskRepository.save(computedPositionAppliedTask);
    }

    private TextMatchingTask applyComputedPosition(TextMatchingTask processedTask, int computedPosition, TextMatch currentBestMatch) {
        if (computedPosition == processedTask.getInputText().length() - 1) {
            return processedTask.newCompletedTask(currentBestMatch);
        }
        return processedTask.newPartlyProcessedTask(computedPosition, currentBestMatch);
    }

    private TextMatch calculateTextMatch(int position, String inputText, String pattern) {
        // Delaying process accordingly to requirements
        simulateArtificialLoad();
        int typosCount = calculateTyposCount(position, inputText, pattern);
        return new TextMatch(position, typosCount);
    }

    private static int calculateTyposCount(int position, String inputText, String pattern) {
        int textMatchingLength = Math.min(inputText.length() - position, pattern.length());
        int additionalCharacters = pattern.length() - textMatchingLength;
        return (int) (IntStream.range(0, textMatchingLength)
                .filter(i -> inputText.charAt(position + i) != pattern.charAt(i))
                .count() + additionalCharacters);
    }

    @SneakyThrows
    private static void simulateArtificialLoad() {
        Thread.sleep(1000);
    }
}
