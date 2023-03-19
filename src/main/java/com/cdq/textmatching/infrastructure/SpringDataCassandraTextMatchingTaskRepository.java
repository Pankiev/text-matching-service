package com.cdq.textmatching.infrastructure;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringDataCassandraTextMatchingTaskRepository extends CrudRepository<CassandraTextMatchingTask, UUID> {
}
