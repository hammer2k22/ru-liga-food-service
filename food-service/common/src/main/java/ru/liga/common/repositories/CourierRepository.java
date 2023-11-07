package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.models.Courier;
import ru.liga.common.models.CourierStatus;

import java.util.List;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    List<Courier> findAllByStatus(CourierStatus status);
}
