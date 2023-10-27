package ru.liga.common.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.models.OrderItem;
import ru.liga.common.models.OrderStatus;
import ru.liga.common.models.RestaurantMenuItem;

import java.util.List;

@Repository
public interface RestaurantMenuItemRepository extends JpaRepository<RestaurantMenuItem, Long> {
    Page<RestaurantMenuItem> findAllByRestaurantId(Pageable pageable, Long id);
}
