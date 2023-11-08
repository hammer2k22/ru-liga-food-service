package ru.liga.deliveryservice.services.rabbitProducerService;


public interface RabbitProducerService {

    void sendMessage(String message, String routingKey);

}
