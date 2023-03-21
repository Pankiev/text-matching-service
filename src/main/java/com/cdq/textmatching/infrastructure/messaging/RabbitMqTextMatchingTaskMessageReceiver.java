package com.cdq.textmatching.infrastructure.messaging;

import com.cdq.textmatching.domain.TextMatchingTaskMessageHandler;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
@Log4j2
class RabbitMqTextMatchingTaskMessageReceiver {

    private final TextMatchingTaskMessageHandler textMatchingTaskMessageHandler;

    @RabbitListener(queues = "${messaging.text-matching-task-queue.name}")
    public void listen(RabbitMqTextMatchingTaskMessage message) {
        log.info("Received message id {}", message.id());
        textMatchingTaskMessageHandler.handle(message.toTextMatchingTaskMessage());
    }
}
