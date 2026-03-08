package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.service.AccountManager;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountManager accountManager;

    // POST /api/accounts  -> creer un compte lie a un customer
    // Body: { "username":"malek123", "password":"123456", "customerId":1 }
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest req) {
        return new ResponseEntity<>(accountManager.create(req), HttpStatus.CREATED);
    }

    // GET /api/accounts
    @GetMapping
    public ResponseEntity<List<AccountResponse>> getAll() {
        return ResponseEntity.ok(accountManager.getAll());
    }

    // GET /api/accounts/1
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountManager.getById(id));
    }

    // GET /api/accounts/customer/1  -> compte du customer_id=1
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<AccountResponse> getByCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(accountManager.getByCustomerId(customerId));
    }

    // DELETE /api/accounts/1
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        accountManager.delete(id);
        return ResponseEntity.ok("Account deleted: " + id);
    }

    // PATCH /api/accounts/1/toggle  -> active ou desactive le compte (is_active)
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<AccountResponse> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(accountManager.toggleActive(id));
    }
}
