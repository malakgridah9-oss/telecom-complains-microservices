package org.example.telecomcomplainscontractservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscontractservice.entity.Contract;
import org.example.telecomcomplainscontractservice.service.ContractServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ContractController {

    private final ContractServiceImpl contractService;

    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        return ResponseEntity.ok(contractService.getAllContracts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable Integer id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Contract>> getContractsByCustomer(@PathVariable Integer customerId) {
        return ResponseEntity.ok(contractService.getContractsByCustomer(customerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Contract>> getContractsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(contractService.getContractsByStatus(status));
    }

    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.createContract(contract));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> updateContract(@PathVariable Integer id,
                                                   @RequestBody Contract contract) {
        return ResponseEntity.ok(contractService.updateContract(id, contract));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContract(@PathVariable Integer id) {
        contractService.deleteContract(id);
        return ResponseEntity.noContent().build();
    }
}