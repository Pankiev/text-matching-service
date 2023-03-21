package com.cdq.textmatching.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "messaging.text-matching-task-queue")
@Data
public class RabbitMqTextMatchingQueueProperties {
    private final String name;
}
