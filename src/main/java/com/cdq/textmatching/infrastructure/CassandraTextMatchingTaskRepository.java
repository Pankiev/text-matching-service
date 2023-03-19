package com.cdq.textmatching.infrastructure;

import com.cdq.textmatching.domain.TextMatchingTask;
import com.cdq.textmatching.domain.TextMatchingTaskRepository;
import com.cdq.textmatching.domain.TextMatchingTaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Repository
public class CassandraTextMatchingTaskRepository implements TextMatchingTaskRepository {

    private final SpringDataCassandraTextMatchingTaskRepository springDataCassandraTextMatchingTaskRepository;

    @Override
    public Optional<TextMatchingTask> findById(UUID id) {
        return springDataCassandraTextMatchingTaskRepository.findById(id)
                .map(this::toTextMatchingTask);
    }

    private TextMatchingTask toTextMatchingTask(CassandraTextMatchingTask cassandraTextMatchingTask) {
        return new TextMatchingTask(
                cassandraTextMatchingTask.getId(),
                TextMatchingTaskStatus.valueOf(cassandraTextMatchingTask.getStatus()),
                cassandraTextMatchingTask.getInputText(),
                cassandraTextMatchingTask.getPattern(),
                cassandraTextMatchingTask.getProgress()
        );
    }

    @Override
    public TextMatchingTask save(TextMatchingTask textMatchingTask) {
        CassandraTextMatchingTask entityToPersist = toCassandraTextMatchingTask(textMatchingTask);
        CassandraTextMatchingTask persistedEntity = springDataCassandraTextMatchingTaskRepository.save(entityToPersist);
        return toTextMatchingTask(persistedEntity);
    }

    private CassandraTextMatchingTask toCassandraTextMatchingTask(TextMatchingTask textMatchingTask) {
        return new CassandraTextMatchingTask(
                textMatchingTask.id(),
                textMatchingTask.status().name(),
                textMatchingTask.inputText(),
                textMatchingTask.pattern(),
                textMatchingTask.taskProgress()
        );
    }

}