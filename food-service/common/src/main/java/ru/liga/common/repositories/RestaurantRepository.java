package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.models.Restaurant;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
