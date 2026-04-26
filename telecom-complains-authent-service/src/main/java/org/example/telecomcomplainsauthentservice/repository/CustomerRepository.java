package org.example.telecomcomplainsauthentservice.repository;

import org.example.telecomcomplainsauthentservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);
    Optional<Customer> findByCin(String cin);
    boolean existsByCin(String cin);
}