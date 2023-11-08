package ru.liga.notificationservice.services.rabbitProducerServices;


public interface RabbitProducerService {

    void sendMessage(String message, String routingKey);

}
