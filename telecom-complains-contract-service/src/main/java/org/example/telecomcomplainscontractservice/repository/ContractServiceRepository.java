package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractServiceRepository
        extends JpaRepository<ContractService, Integer> {

    List<ContractService> findByContractId(Integer contractId);
    List<ContractService> findByStatus(String status);
}