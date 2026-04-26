package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.AccountRequest;
import org.example.customerservice.dto.AccountResponse;
import org.example.customerservice.service.AccountManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountManager accountManager;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        return new ResponseEntity<>(accountManager.create(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll() {
        return ResponseEntity.ok(accountManager.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountManager.getById(id));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<AccountResponse> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountManager.getByCustomerId(customerId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AccountResponse> getByUsername(@PathVariable String username) {
        return ResponseEntity.ok(accountManager.getByUsername(username));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountManager.update(id, request));
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<AccountResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(accountManager.toggleActive(id));
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<AccountResponse> credit(@PathVariable Long id,
                                                  @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountManager.credit(id, amount));
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<AccountResponse> debit(@PathVariable Long id,
                                                 @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(accountManager.debit(id, amount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        accountManager.delete(id);
        return ResponseEntity.ok("Account deleted: " + id);
    }
}