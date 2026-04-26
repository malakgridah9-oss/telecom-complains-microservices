package org.example.telecomcomplainscontractservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.dto.ContractDetailResponse;
import org.example.telecomcomplainscontractservice.entity.Contract;
import org.example.telecomcomplainscontractservice.service.ContractServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractServiceImpl contractService;

    @GetMapping
    public ResponseEntity<List<ContractDetailResponse>> getAllContracts() {
        List<Contract> contracts = contractService.getAllContracts();
        List<ContractDetailResponse> result = new ArrayList<>();
        for (Contract c : contracts) {
            result.add(contractService.getContractDetail(c.getContractId()));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getContractById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(contractService.getContractDetail(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ContractDetailResponse>> getContractsByCustomer(
            @PathVariable Integer customerId) {
        return ResponseEntity.ok(contractService.getContractsByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Contract>> getContractsByStatus(
            @PathVariable String status) {
        return ResponseEntity.ok(contractService.getContractsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        contract.setContractId(null);
        return ResponseEntity.status(201).body(contractService.createContract(contract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContract(
            @PathVariable Integer id,
            @RequestBody Contract contract) {
        try {
            return ResponseEntity.ok(contractService.updateContract(id, contract));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Integer id) {
        try {
            contractService.deleteContract(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}