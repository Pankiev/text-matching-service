package com.cdq.textmatching.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
    @Bean
    public Queue textMatchingTaskQueue(@Value("${messaging.textMatchingTaskQueue.name}") String textMatchingTaskQueueName) {
        return new Queue(textMatchingTaskQueueName, true);
    }
}
