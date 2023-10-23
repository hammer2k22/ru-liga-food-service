package ru.liga.kitchenservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.kitchenservice.models.RestaurantMenuItem;


public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {


}
