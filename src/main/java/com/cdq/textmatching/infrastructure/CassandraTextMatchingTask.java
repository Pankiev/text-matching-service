package com.cdq.textmatching.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table("text_matching_tasks")
public class CassandraTextMatchingTask {

    @PrimaryKey
    private UUID id;
    private String status;
    @Column("input_text")
    private String inputText;
    private String pattern;
    private BigDecimal progress;
}
