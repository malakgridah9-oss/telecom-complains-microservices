package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.ContractService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractServiceRepository extends JpaRepository<ContractService, Integer> {
    List<ContractService> findByContractId(Integer contractId);
}