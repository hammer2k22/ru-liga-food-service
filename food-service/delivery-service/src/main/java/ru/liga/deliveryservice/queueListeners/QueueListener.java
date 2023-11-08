package ru.liga.deliveryservice.queueListeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitMessage;
import ru.liga.deliveryservice.services.DeliveryService;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final ObjectMapper mapper;
    private final DeliveryService deliveryService;

    @RabbitListener(queues = "deliveryQueue")
    public void processMyQueue(String message) throws JsonProcessingException {

        RabbitMessage rabbitMessage = mapper.readValue(message, RabbitMessage.class);

        System.out.println(rabbitMessage.getMessage() + " id = " + rabbitMessage.getOrderId());

        deliveryService.searchAvailableCourier(rabbitMessage.getOrderId());


    }
}
