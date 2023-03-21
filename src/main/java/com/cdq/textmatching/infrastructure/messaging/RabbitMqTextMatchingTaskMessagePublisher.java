package com.cdq.textmatching.infrastructure.messaging;

import com.cdq.textmatching.configuration.RabbitMqTextMatchingQueueProperties;
import com.cdq.textmatching.domain.TextMatchingTaskMessage;
import com.cdq.textmatching.domain.TextMatchingTaskMessagePublisher;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class RabbitMqTextMatchingTaskMessagePublisher implements TextMatchingTaskMessagePublisher {

    private final AmqpTemplate amqpTemplate;
    private final RabbitMqTextMatchingQueueProperties rabbitMqTextMatchingQueueProperties;

    @Override
    public void publish(TextMatchingTaskMessage textMatchingTaskMessage) {
        amqpTemplate.convertAndSend(rabbitMqTextMatchingQueueProperties.getName(),
                RabbitMqTextMatchingTaskMessage.of(textMatchingTaskMessage));
    }
}
