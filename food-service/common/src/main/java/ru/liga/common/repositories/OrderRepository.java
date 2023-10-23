package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
