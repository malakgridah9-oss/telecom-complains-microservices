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
public class AccountManager {

    private final AccountRepository  accountRepo;
    private final CustomerRepository customerRepo;

    public AccountResponse create(AccountRequest req) {
        if (accountRepo.existsByUsername(req.getUsername()))
            throw new DuplicateResourceException("Username already exists: " + req.getUsername());

        // Lie le compte au customer via customer_id
        Customer customer = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.getCustomerId()));

        Account saved = accountRepo.save(Account.builder()
                .username(req.getUsername())
                .password(req.getPassword())
                .isActive(req.getIsActive() != null ? req.getIsActive() : true)
                .customer(customer)
                .build());
        return map(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(Long id) { return map(find(id)); }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAll() {
        return accountRepo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponse getByCustomerId(Long customerId) {
        return map(accountRepo.findByCustomerCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for customer: " + customerId)));
    }

    public void delete(Long id) { accountRepo.delete(find(id)); }

    // Activer ou desactiver le compte (is_active toggle)
    public AccountResponse toggleActive(Long id) {
        Account a = find(id);
        a.setIsActive(!a.getIsActive());
        return map(accountRepo.save(a));
    }

    private Account find(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
    }

    private AccountResponse map(Account a) {
        AccountResponse r = new AccountResponse();
        r.setId(a.getId()); r.setUsername(a.getUsername());
        r.setIsActive(a.getIsActive()); r.setCreatedAt(a.getCreatedAt());
        if (a.getCustomer() != null) {
            r.setCustomerId(a.getCustomer().getCustomerId());
            r.setCustomerFullName(a.getCustomer().getFullName());
        }
        return r;
    }
}
