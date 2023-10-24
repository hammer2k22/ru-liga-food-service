package ru.liga.common.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
