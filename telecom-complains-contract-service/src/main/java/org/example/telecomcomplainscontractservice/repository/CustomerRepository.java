package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}