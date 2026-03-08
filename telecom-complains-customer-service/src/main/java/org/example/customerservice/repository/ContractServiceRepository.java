package org.example.customerservice.repository;
import org.example.customerservice.entity.ContractService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ContractServiceRepository extends JpaRepository<ContractService, Long> {
    List<ContractService> findByContractId(Long contractId);
    List<ContractService> findByTelecomServiceId(Long serviceId);
}
