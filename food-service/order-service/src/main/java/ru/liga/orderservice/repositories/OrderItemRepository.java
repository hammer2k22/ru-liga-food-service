package ru.liga.orderservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.orderservice.models.Order;
import ru.liga.orderservice.models.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
