package ru.liga.kitchenservice.services.rabbitProducerService;


public interface RabbitProducerService {

    void sendMessage(String message, String routingKey);

}
