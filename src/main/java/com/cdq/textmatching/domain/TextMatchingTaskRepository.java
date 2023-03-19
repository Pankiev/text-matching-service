package com.cdq.textmatching.domain;


import java.util.Optional;
import java.util.UUID;

public interface TextMatchingTaskRepository {

    Optional<TextMatchingTask> findById(UUID id);

    TextMatchingTask save(TextMatchingTask textMatchingTask);
}
