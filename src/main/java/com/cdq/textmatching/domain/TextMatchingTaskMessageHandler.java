package com.cdq.textmatching.domain;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@AllArgsConstructor
@Log4j2
public class TextMatchingTaskMessageHandler {

    private final TextMatchingTaskRepository textMatchingTaskRepository;
    private final TextMatchingTaskProcessor textMatchingTaskProcessor;

    public void handle(TextMatchingTaskMessage textMatchingTaskMessage) {
        TextMatchingTask taskToProcess = provideTaskToProcess(textMatchingTaskMessage);
        //  Based on the assumption that each step of the text processing algorithm is "heavy" (currently simulated by
        //  Thread.sleep()) we can update progress in the database directly each time without any conditions. If it was not the
        //  case we should persist progress at the start of computation, then periodically and then finally at the end. Possibly
        //  we could think of centralised cache if even better performance is required.
        textMatchingTaskProcessor.process(taskToProcess, textMatchingTaskRepository::save);
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
}
