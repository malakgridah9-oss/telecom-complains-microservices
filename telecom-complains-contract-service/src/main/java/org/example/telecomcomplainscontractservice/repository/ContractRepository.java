package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    List<Contract> findByStatus(String status);
}