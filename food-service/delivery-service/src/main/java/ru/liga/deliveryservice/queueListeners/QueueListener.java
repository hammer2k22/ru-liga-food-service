package ru.liga.deliveryservice.queueListeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.services.DeliveryService;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final DeliveryService deliveryService;

    @RabbitListener(queues = "deliveryQueue")
    public void processMyQueue(String message) {

        long orderId = Long.parseLong(message.substring(0, message.indexOf('.')));
        String orderStatus = message.substring(message.indexOf('.')+1);

        if(orderStatus.equals("DELIVERY_WAITING")){
            deliveryService.searchAvailableCourier(orderId);
        }

    }
}
