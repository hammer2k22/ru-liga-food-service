package ru.liga.kitchenservice.queueListeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class QueueListener {

    @RabbitListener(queues = "kitchenQueue")
    public void processMyQueue(String message) {
        System.out.println(message);

    }
}
