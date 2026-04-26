package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.entity.*;
import org.example.customerservice.exception.*;
import org.example.customerservice.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountManager {

    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final PasswordEncoder passwordEncoder;  // ⭐ Utiliser PasswordEncoder (interface)

    public AccountResponse create(AccountRequest req) {
        // Verifier si username existe deja
        if (accountRepo.existsByUsername(req.getUsername()))
            throw new DuplicateResourceException("Username already exists: " + req.getUsername());

        // Verifier si customer existe
        customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.getCustomerId()));

        // Creer le compte
        Account saved = accountRepo.save(Account.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .accountCode(generateAccountCode())
                .accountType(req.getAccountType())
                .balance(req.getBalance() != null ? req.getBalance() : BigDecimal.ZERO)
                .isActive(req.getIsActive() != null ? req.getIsActive() : true)
                .customerId(req.getCustomerId())
                .createdAt(LocalDateTime.now())
                .build());

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(Long id) {
        return toResponse(find(id));
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAll() {
        return accountRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponse getByCustomerId(Long customerId) {
        Account account = accountRepo.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for customer: " + customerId));
        return toResponse(account);
    }

    @Transactional(readOnly = true)
    public AccountResponse getByUsername(String username) {
        Account account = accountRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for username: " + username));
        return toResponse(account);
    }

    public AccountResponse update(Long id, AccountRequest req) {
        Account account = find(id);

        if (req.getUsername() != null && !req.getUsername().equals(account.getUsername())) {
            if (accountRepo.existsByUsername(req.getUsername())) {
                throw new DuplicateResourceException("Username already exists: " + req.getUsername());
            }
            account.setUsername(req.getUsername());
        }

        if (req.getPassword() != null && !req.getPassword().isEmpty()) {
            account.setPassword(passwordEncoder.encode(req.getPassword()));
        }

        if (req.getAccountType() != null) {
            account.setAccountType(req.getAccountType());
        }

        if (req.getBalance() != null) {
            account.setBalance(req.getBalance());
        }

        if (req.getIsActive() != null) {
            account.setIsActive(req.getIsActive());
        }

        accountRepo.save(account);
        return toResponse(account);
    }

    public AccountResponse toggleActive(Long id) {
        Account account = find(id);
        account.setIsActive(!account.getIsActive());
        accountRepo.save(account);
        return toResponse(account);
    }

    public void delete(Long id) {
        accountRepo.delete(find(id));
    }

    public AccountResponse credit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account account = find(id);
        account.setBalance(account.getBalance().add(amount));
        accountRepo.save(account);

        return toResponse(account);
    }

    public AccountResponse debit(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account account = find(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepo.save(account);

        return toResponse(account);
    }

    private Account find(Long id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }

    private String generateAccountCode() {
        return "ACC-" + System.currentTimeMillis();
    }

    private AccountResponse toResponse(Account account) {
        String customerName = customerRepo.findById(account.getCustomerId())
                .map(Customer::getFullName)
                .orElse("Inconnu");

        return new AccountResponse(
                account.getAccountId(),
                account.getCustomerId(),
                customerName,
                account.getAccountCode(),
                account.getAccountType(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getIsActive(),
                account.getUsername()
        );
    }
}