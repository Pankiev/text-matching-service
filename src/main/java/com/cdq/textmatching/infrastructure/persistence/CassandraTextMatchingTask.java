package com.cdq.textmatching.infrastructure.persistence;

import com.cdq.textmatching.domain.TextMatch;
import com.cdq.textmatching.domain.TextMatchingTask;
import com.cdq.textmatching.domain.TextMatchingTaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Table("text_matching_tasks")
class CassandraTextMatchingTask {

    @PrimaryKey
    private UUID id;
    private String status;
    @Column("input_text")
    private String inputText;
    private String pattern;
    @Column("last_checked_index")
    private Integer lastCheckedIndex;
    @Column("position_in_input_text")
    private Integer positionInInputText;
    @Column("typos_count")
    private Integer typosCount;

    static CassandraTextMatchingTask of(TextMatchingTask textMatchingTask) {
        return new CassandraTextMatchingTask(
                textMatchingTask.getId(),
                textMatchingTask.getStatus().name(),
                textMatchingTask.getInputText(),
                textMatchingTask.getPattern(),
                textMatchingTask.getLastCheckedPosition().orElse(null),
                textMatchingTask.getBestMatch().map(TextMatch::positionInInputText).orElse(null),
                textMatchingTask.getBestMatch().map(TextMatch::typosCount).orElse(null)
        );
    }

    TextMatchingTask toTextMatchingTask() {
        return new TextMatchingTask(
                id,
                TextMatchingTaskStatus.valueOf(status),
                inputText,
                pattern,
                lastCheckedIndex,
                Optional.ofNullable(positionInInputText).map(position -> new TextMatch(positionInInputText, typosCount)).orElse(null)
        );
    }
}
