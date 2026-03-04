package org.example.telecomcomplainscontractservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.entity.Contract;
import org.example.telecomcomplainscontractservice.repository.ContractRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl {

    private final ContractRepository contractRepository;

    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContractById(Integer id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé : " + id));
    }

    public List<Contract> getContractsByStatus(String status) {
        return contractRepository.findByStatus(status);
    }

    public Contract createContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public Contract updateContract(Integer id, Contract contractDetails) {
        Contract contract = getContractById(id);
        contract.setMsisdn(contractDetails.getMsisdn());
        contract.setStatus(contractDetails.getStatus());
        contract.setStartDate(contractDetails.getStartDate());
        return contractRepository.save(contract);
    }

    public void deleteContract(Integer id) {
        contractRepository.deleteById(id);
    }

    public List<Contract> getContractsByCustomer(Integer customerId) {
        return List.of();
    }
}