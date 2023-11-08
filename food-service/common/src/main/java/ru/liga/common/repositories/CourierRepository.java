package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.models.Courier;
import ru.liga.common.models.enums.CourierStatus;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findAllByStatus(CourierStatus courierAvailable);
}