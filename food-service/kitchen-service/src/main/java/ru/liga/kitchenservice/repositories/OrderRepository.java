package ru.liga.kitchenservice.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.kitchenservice.models.Order;
import ru.liga.kitchenservice.models.OrderStatus;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

        Page<Order> findAllByStatus(Pageable pageable, OrderStatus status);
}
