package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.entity.*;
import org.example.customerservice.exception.*;
import org.example.customerservice.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractServiceImpl implements ContractService {

    private final ContractRepository  contractRepo;
    private final CustomerRepository  customerRepo;

    @Override
    public ContractResponse create(ContractRequest req) {
        if (contractRepo.existsByContractNumber(req.getContractNumber()))
            throw new DuplicateResourceException("Contract number already exists: " + req.getContractNumber());

        // Cherche le customer par customer_id (FK dans la table contract)
        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.getCustomerId()));

        Contract saved = contractRepo.save(Contract.builder()
                .contractNumber(req.getContractNumber())
                .customer(customer)
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isActive(req.getIsActive() != null ? req.getIsActive() : true)
                .build());
        return map(saved);
    }

    @Override @Transactional(readOnly = true)
    public ContractResponse getById(Long id) { return map(find(id)); }

    @Override @Transactional(readOnly = true)
    public List<ContractResponse> getAll() {
        return contractRepo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public ContractResponse update(Long id, ContractRequest req) {
        Contract c = find(id);
        c.setStartDate(req.getStartDate());
        c.setEndDate(req.getEndDate());
        if (req.getIsActive() != null) c.setIsActive(req.getIsActive());
        return map(contractRepo.save(c));
    }

    @Override
    public void delete(Long id) { contractRepo.delete(find(id)); }

    @Override @Transactional(readOnly = true)
    public List<ContractResponse> getByCustomerId(Long customerId) {
        return contractRepo.findByCustomerCustomerId(customerId)
                .stream().map(this::map).collect(Collectors.toList());
    }

    private Contract find(Long id) {
        return contractRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contract not found: " + id));
    }

    private ContractResponse map(Contract c) {
        ContractResponse r = new ContractResponse();
        r.setId(c.getId());
        r.setContractNumber(c.getContractNumber());
        r.setStartDate(c.getStartDate());
        r.setEndDate(c.getEndDate());
        r.setIsActive(c.getIsActive());
        r.setCreatedAt(c.getCreatedAt());
        if (c.getCustomer() != null) {
            r.setCustomerId(c.getCustomer().getCustomerId());
            r.setCustomerFullName(c.getCustomer().getFullName());
        }
        return r;
    }
}
