package ru.liga.notificationservice.services.queueListeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.common.dto.RabbitMessage;
import ru.liga.notificationservice.services.rabbitProducerServices.RabbitProducerServiceImpl;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final RabbitProducerServiceImpl rabbitProducerService;
    private final ObjectMapper mapper;

    @RabbitListener(queues = "notificationQueue")
    public void processMyQueue(String message) throws JsonProcessingException {

        RabbitMessage rabbitMessage = mapper.readValue(message, RabbitMessage.class);

        String queueToSend = rabbitMessage.getQueueToSend();

        rabbitProducerService.sendMessage(message, queueToSend);

    }
}
