package com.cdq.textmatching.domain;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TextMatchingTaskRepository {

    List<TextMatchingTask> findAll();

    Optional<TextMatchingTask> findById(UUID id);

    TextMatchingTask save(TextMatchingTask textMatchingTask);
}
