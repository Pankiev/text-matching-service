package com.cdq.textmatching.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class TextMatchingTaskMessageReceiver {

    @RabbitListener(queues = "${messaging.textMatchingTaskQueue.name}", concurrency = "${messaging.textMatchingTaskQueue.consumer.concurrency}")
    public void listen(String in) {
        System.out.println("Message read from myQueue : " + in);
    }
}
