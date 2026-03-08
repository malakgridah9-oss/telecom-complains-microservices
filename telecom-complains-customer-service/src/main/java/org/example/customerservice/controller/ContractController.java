package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.service.ContractService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // POST /api/contracts
    // Body: { "contractNumber":"CTR-001", "customerId":1, "startDate":"2024-01-01", "endDate":"2025-01-01" }
    @PostMapping
    public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractRequest req) {
        return new ResponseEntity<>(contractService.create(req), HttpStatus.CREATED);
    }

    // GET /api/contracts
    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAll() {
        return ResponseEntity.ok(contractService.getAll());
    }

    // GET /api/contracts/1
    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    // PUT /api/contracts/1
    @PutMapping("/{id}")
    public ResponseEntity<ContractResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody ContractRequest req) {
        return ResponseEntity.ok(contractService.update(id, req));
    }

    // DELETE /api/contracts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contractService.delete(id);
        return ResponseEntity.ok("Contract deleted: " + id);
    }

    // GET /api/contracts/customer/1  -> tous les contrats du customer_id=1
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ContractResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(contractService.getByCustomerId(customerId));
    }
}
