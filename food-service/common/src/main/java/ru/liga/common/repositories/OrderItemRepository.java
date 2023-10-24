package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.models.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
