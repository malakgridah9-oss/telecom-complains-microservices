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
@CrossOrigin(origins = "http://localhost:4200")  // ← AJOUTE ICI une seule fois
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<ContractResponse> create(@Valid @RequestBody ContractRequest req) {
        return new ResponseEntity<>(contractService.create(req), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAll() {
        return ResponseEntity.ok(contractService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(contractService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody ContractRequest req) {
        return ResponseEntity.ok(contractService.update(id, req));
    }

    @DeleteMapping("/{id}")   // ← UNE SEULE version
    public ResponseEntity<String> delete(@PathVariable Long id) {
        contractService.delete(id);
        return ResponseEntity.ok("Contract deleted: " + id);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ContractResponse>> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(contractService.getByCustomerId(customerId));
    }
}