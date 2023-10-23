package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.models.RestaurantMenuItem;


public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {

}
