package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractRepository
        extends JpaRepository<Contract, Integer> {

    List<Contract> findByCustomerId(Integer customerId);
    List<Contract> findByStatus(String status);
}