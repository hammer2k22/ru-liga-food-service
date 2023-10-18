package ru.liga.orderservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.orderservice.models.Order;
import ru.liga.orderservice.models.RestaurantMenuItem;


public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

}
