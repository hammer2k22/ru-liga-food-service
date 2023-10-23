package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.models.OrderItem;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
