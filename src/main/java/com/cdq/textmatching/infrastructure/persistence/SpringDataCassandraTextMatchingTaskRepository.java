package com.cdq.textmatching.infrastructure.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface SpringDataCassandraTextMatchingTaskRepository extends CrudRepository<CassandraTextMatchingTask, UUID> {
}
