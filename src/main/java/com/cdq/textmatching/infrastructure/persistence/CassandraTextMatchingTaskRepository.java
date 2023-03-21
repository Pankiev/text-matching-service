package com.cdq.textmatching.infrastructure.persistence;

import com.cdq.textmatching.domain.TextMatchingTask;
import com.cdq.textmatching.domain.TextMatchingTaskRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@Repository
@Log4j2
class CassandraTextMatchingTaskRepository implements TextMatchingTaskRepository {

    private final SpringDataCassandraTextMatchingTaskRepository springDataCassandraTextMatchingTaskRepository;


    @Override
    public List<TextMatchingTask> findAll() {
        return StreamSupport.stream(springDataCassandraTextMatchingTaskRepository.findAll().spliterator(), false)
                .map(CassandraTextMatchingTask::toTextMatchingTask)
                .toList();
    }


    @Override
    public Optional<TextMatchingTask> findById(UUID id) {
        return springDataCassandraTextMatchingTaskRepository.findById(id)
                .map(CassandraTextMatchingTask::toTextMatchingTask);
    }

    @Override
    public TextMatchingTask save(TextMatchingTask textMatchingTask) {
        CassandraTextMatchingTask entityToPersist = CassandraTextMatchingTask.of(textMatchingTask);
        CassandraTextMatchingTask persistedEntity = springDataCassandraTextMatchingTaskRepository.save(entityToPersist);
        log.info("Persisted text matching task with id: {}", persistedEntity.getId());
        return persistedEntity.toTextMatchingTask();
    }

}
