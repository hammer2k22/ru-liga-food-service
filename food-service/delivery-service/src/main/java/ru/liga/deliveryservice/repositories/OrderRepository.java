package ru.liga.deliveryservice.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.deliveryservice.models.Order;
import ru.liga.deliveryservice.models.OrderStatus;


public interface OrderRepository extends JpaRepository<Order, Long> {

        Page<Order> findAllByStatus(Pageable pageable, OrderStatus status);
}
