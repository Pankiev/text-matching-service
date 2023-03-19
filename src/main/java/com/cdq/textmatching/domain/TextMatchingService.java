package com.cdq.textmatching.domain;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class TextMatchingService {

    private final TextMatchingTaskRepository textMatchingTaskRepository;

    public TextMatchingTask startMatchingTask(String inputText, String pattern) {
        TextMatchingTask textMatchingTask = TextMatchingTask.newTaskToProcess(inputText, pattern);
        return textMatchingTaskRepository.save(textMatchingTask);
    }

    public Optional<TextMatchingTask> findTextMatchingTask(UUID id) {
        return textMatchingTaskRepository.findById(id);
    }
}
