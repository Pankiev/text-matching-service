package com.cdq.textmatching.domain;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.IntStream;

@Log4j2
@AllArgsConstructor
public class TextMatchingTaskProcessor {

    private final ArtificialLoadSimulator artificialLoadSimulator;

    public void process(TextMatchingTask taskToProcess, Consumer<TextMatchingTask> udpatedTaskConsumer) {
        log.info("Processing task id {}", taskToProcess.getId());
        TextMatchingTask partiallyComputedTask = processFirstPositionIfRequired(taskToProcess, udpatedTaskConsumer);
        TextMatchingTask finishedTask = processPartiallyComputedTask(partiallyComputedTask, udpatedTaskConsumer);
        log.info("Processing task id {} finished. Best match: {}", finishedTask.getId(), finishedTask.getBestMatch().orElseThrow());
    }

    private TextMatchingTask processPartiallyComputedTask(TextMatchingTask partiallyComputedTask, Consumer<TextMatchingTask> udpatedTaskConsumer) {
        TextMatchingTask currentTask = partiallyComputedTask;
        while (currentTask.requiresProcessing()) {
            currentTask = processNextInputTextPosition(currentTask, udpatedTaskConsumer);
            log.info("Processed {} out of {} positions",
                    currentTask.getLastCheckedPosition().orElseThrow() + 1,
                    currentTask.getInputText().length());
        }
        return currentTask;
    }

    private TextMatchingTask processNextInputTextPosition(TextMatchingTask partiallyComputedTask, Consumer<TextMatchingTask> udpatedTaskConsumer) {
        int positionToCompute = partiallyComputedTask.getLastCheckedPosition().orElseThrow(IllegalArgumentException::new) + 1;
        TextMatch currentBestMatch = partiallyComputedTask.getBestMatch().orElseThrow(IllegalArgumentException::new);
        TextMatch newMatch = calculateTextMatch(positionToCompute, partiallyComputedTask.getInputText(), partiallyComputedTask.getPattern());
        TextMatch newBestMatch = newMatch.matchesBetterThan(currentBestMatch) ? newMatch : currentBestMatch;
        log.info("Text match for task with id {} calculated: {}", partiallyComputedTask.getId(), newMatch);
        return emitUpdatedTask(partiallyComputedTask, positionToCompute, newBestMatch, udpatedTaskConsumer);
    }

    private TextMatchingTask processFirstPositionIfRequired(TextMatchingTask taskToProcess, Consumer<TextMatchingTask> udpatedTaskConsumer) {
        Optional<TextMatch> currentBestMatch = taskToProcess.getBestMatch();
        if (currentBestMatch.isEmpty()) {
            TextMatch firstPositionTextMatch = calculateTextMatch(0, taskToProcess.getInputText(), taskToProcess.getPattern());
            log.info("Text match for task with id {} calculated: {}", taskToProcess.getId(), firstPositionTextMatch);
            return emitUpdatedTask(taskToProcess, 0, firstPositionTextMatch, udpatedTaskConsumer);
        }
        return taskToProcess;
    }

    private TextMatchingTask emitUpdatedTask(TextMatchingTask processedTask, int computedPosition,
                                             TextMatch currentBestMatch, Consumer<TextMatchingTask> udpatedTaskConsumer) {
        TextMatchingTask computedPositionAppliedTask = applyComputedPosition(processedTask, computedPosition, currentBestMatch);
        udpatedTaskConsumer.accept(computedPositionAppliedTask);
        return computedPositionAppliedTask;
    }

    private TextMatchingTask applyComputedPosition(TextMatchingTask processedTask, int computedPosition, TextMatch currentBestMatch) {
        if (computedPosition == processedTask.getInputText().length() - 1) {
            return processedTask.newCompletedTask(currentBestMatch);
        }
        return processedTask.newPartlyProcessedTask(computedPosition, currentBestMatch);
    }

    private TextMatch calculateTextMatch(int position, String inputText, String pattern) {
        // Delaying process accordingly to requirements
        artificialLoadSimulator.simulateArtificialLoad();
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
}
