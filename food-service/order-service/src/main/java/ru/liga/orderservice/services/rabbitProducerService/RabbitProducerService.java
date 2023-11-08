package ru.liga.orderservice.services.rabbitProducerService;


public interface RabbitProducerService {

    void sendMessage(String message, String routingKey);

}
