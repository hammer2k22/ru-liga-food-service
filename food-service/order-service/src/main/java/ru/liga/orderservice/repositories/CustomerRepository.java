package ru.liga.orderservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.orderservice.models.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
