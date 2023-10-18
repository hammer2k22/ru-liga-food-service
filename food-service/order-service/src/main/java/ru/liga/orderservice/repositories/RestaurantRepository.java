package ru.liga.orderservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.orderservice.models.Restaurant;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
