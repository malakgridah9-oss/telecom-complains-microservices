package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.service.CustomerService;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // ─── CRUD EXISTANTS ──────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")  // Seul ADMIN peut créer
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest req) {
        return new ResponseEntity<>(customerService.create(req), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")  // ADMIN et AGENT peuvent voir
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")  // ADMIN et AGENT peuvent voir
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getById(customerId));
    }

    @PutMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")  // Seul ADMIN peut modifier
    public ResponseEntity<CustomerResponse> update(@PathVariable Long customerId,
                                                   @Valid @RequestBody CustomerRequest req) {
        return ResponseEntity.ok(customerService.update(customerId, req));
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasRole('ADMIN')")  // Seul ADMIN peut supprimer
    public ResponseEntity<String> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.ok("Customer deleted: " + customerId);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")  // ADMIN et AGENT peuvent voir
    public ResponseEntity<CustomerResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }

    @GetMapping("/phone/{phone}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")  // ADMIN et AGENT peuvent voir
    public ResponseEntity<CustomerResponse> getByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(customerService.getByPhone(phone));
    }

    // ═══════════════════════════════════════════════════════════════
    // ⭐ NOUVEAUX ENDPOINTS POUR LES CLIENTS AVEC CONTRATS
    // ═══════════════════════════════════════════════════════════════

    @GetMapping("/with-contracts")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<List<CustomerResponse>> getCustomersWithContracts() {
        return ResponseEntity.ok(customerService.getCustomersWithContracts());
    }

    @GetMapping("/without-contracts")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<List<CustomerResponse>> getCustomersWithoutContracts() {
        return ResponseEntity.ok(customerService.getCustomersWithoutContracts());
    }

    @GetMapping("/{customerId}/has-contract")
    @PreAuthorize("hasAnyRole('ADMIN', 'AGENT')")
    public ResponseEntity<Boolean> hasContract(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.hasContract(customerId));
    }
}