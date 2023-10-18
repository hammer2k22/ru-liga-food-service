package ru.liga.orderservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.orderservice.models.Order;




public interface OrderRepository extends JpaRepository<Order, Long> {

}
