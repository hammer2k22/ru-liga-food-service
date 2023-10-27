package ru.liga.notificationservice.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingMQConfig {


    @Bean
    public Declarables kitchenQueue() {
        Queue queueDirectFirst = new Queue("kitchenQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueDirectFirst,  directExchange,
                BindingBuilder.bind(queueDirectFirst).to(directExchange).with("kitchen"));
    }

    @Bean
    public Declarables orderQueue() {
        Queue queueDirectFirst = new Queue("orderQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueDirectFirst,  directExchange,
                BindingBuilder.bind(queueDirectFirst).to(directExchange).with("order"));
    }

    @Bean
    public Declarables deliveryQueue() {
        Queue queueDirectFirst = new Queue("deliveryQueue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueDirectFirst,  directExchange,
                BindingBuilder.bind(queueDirectFirst).to(directExchange).with("delivery"));
    }

}
