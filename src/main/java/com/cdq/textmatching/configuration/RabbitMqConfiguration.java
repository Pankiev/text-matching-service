package com.cdq.textmatching.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RabbitMqTextMatchingQueueProperties.class)
public class RabbitMqConfiguration {
    @Bean
    public Queue textMatchingTaskQueue(RabbitMqTextMatchingQueueProperties queueProperties) {
        return new Queue(queueProperties.getName(), true);
    }
}
