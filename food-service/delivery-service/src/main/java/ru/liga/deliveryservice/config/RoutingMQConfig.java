package ru.liga.deliveryservice.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {


    @Bean
    public Declarables notificationQueue() {
        Queue queueDirectFirst = new Queue("notificationQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueDirectFirst,  directExchange,
                BindingBuilder.bind(queueDirectFirst).to(directExchange).with("notification"));
    }

}
