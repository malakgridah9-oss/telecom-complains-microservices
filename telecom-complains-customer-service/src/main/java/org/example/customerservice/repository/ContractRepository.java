package org.example.customerservice.repository;

import org.example.customerservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findByCustomerId(Long customerId);
    Optional<Contract> findByContractNumber(String contractNumber);
    List<Contract> findByIsActive(Boolean isActive);
    boolean existsByContractNumber(String contractNumber);
}
