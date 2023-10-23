package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.common.models.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
