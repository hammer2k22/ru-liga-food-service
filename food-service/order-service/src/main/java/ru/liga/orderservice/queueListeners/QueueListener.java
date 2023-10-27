package ru.liga.orderservice.queueListeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueListener {


    @RabbitListener(queues = "orderQueue")
    public void processMyQueue(String message) {
        System.out.println(message);
    }
}
