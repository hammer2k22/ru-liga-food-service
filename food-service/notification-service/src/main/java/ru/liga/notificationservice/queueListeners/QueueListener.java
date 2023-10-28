package ru.liga.notificationservice.queueListeners;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.notificationservice.services.rabbitProducerServices.RabbitProducerServiceImpl;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final RabbitProducerServiceImpl rabbitProducerService;

    @RabbitListener(queues = "notificationQueue")
    public void processMyQueue(String message) {
        long orderId = Long.parseLong(message.substring(0, message.indexOf('.')));
        String orderStatus = message.substring(message.indexOf('.')+1);

        if(orderStatus.equals("CUSTOMER_PAID")){

            String orderMessage = "Order with id "+orderId+" has been paid";
            String kitchenMessage = "New order with id "+orderId;
            rabbitProducerService.sendMessage(orderMessage,"order");
            rabbitProducerService.sendMessage(kitchenMessage,"kitchen");

        }
        if(orderStatus.equals("KITCHEN_DENIED")){

            String orderMessage = "Kitchen denied the order with id "+ orderId+
                    ". The refund process is underway.";
            rabbitProducerService.sendMessage(orderMessage,"order");

        }

        if(orderStatus.equals("KITCHEN_ACCEPTED")){

            String orderMessage = "Kitchen accepted the order with id "+ orderId;
            rabbitProducerService.sendMessage(orderMessage,"order");

        }

        if(orderStatus.equals("KITCHEN_PREPARING")){

            String orderMessage = "Kitchen preparing the order with id "+ orderId;
            rabbitProducerService.sendMessage(orderMessage,"order");

        }

        if(orderStatus.equals("DELIVERY_WAITING")){

            String orderMessage = "Kitchen prepared the order with id "+orderId+". Delivery awaited.";
            rabbitProducerService.sendMessage(orderMessage,"order");
            rabbitProducerService.sendMessage(message,"delivery");
        }

        if(orderStatus.equals("COURIER_ACCEPTED")){

            String kitchenMessage = "Courier accept the order with id "+orderId+". Delivery awaited.";
            rabbitProducerService.sendMessage(kitchenMessage,"kitchen");
            rabbitProducerService.sendMessage(message,"delivery");
        }

        if(orderStatus.equals("DELIVERY_PICKING")){

            String orderMessage = "Courier picked the order with id "+orderId;
            rabbitProducerService.sendMessage(orderMessage,"order");
        }

        if(orderStatus.equals("DELIVERY_COMPLETE")){

            String orderMessage = "Order with id "+orderId+" complete";
            rabbitProducerService.sendMessage(orderMessage,"order");
        }

    }
}
