package org.example.customerservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.service.CustomerService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // POST /api/customers
    // Body: { "fullName":"Malek Ben Ali", "email":"malek@gmail.com", "phone":"0612345678", "address":"Tunis" }
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest req) {
        return new ResponseEntity<>(customerService.create(req), HttpStatus.CREATED);
    }

    // GET /api/customers  -> liste tous les clients de la table customer
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.getAll());
    }

    // GET /api/customers/1  -> trouve par customer_id
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.getById(customerId));
    }

    // PUT /api/customers/1  -> met a jour full_name, email, phone, address
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long customerId,
                                                    @Valid @RequestBody CustomerRequest req) {
        return ResponseEntity.ok(customerService.update(customerId, req));
    }

    // DELETE /api/customers/1
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> delete(@PathVariable Long customerId) {
        customerService.delete(customerId);
        return ResponseEntity.ok("Customer deleted: " + customerId);
    }

    // GET /api/customers/email/malek@gmail.com
    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getByEmail(email));
    }

    // GET /api/customers/phone/0612345678
    @GetMapping("/phone/{phone}")
    public ResponseEntity<CustomerResponse> getByPhone(@PathVariable String phone) {
        return ResponseEntity.ok(customerService.getByPhone(phone));
    }

    // GET /api/customers/search?keyword=malek
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(customerService.search(keyword));
    }
}
