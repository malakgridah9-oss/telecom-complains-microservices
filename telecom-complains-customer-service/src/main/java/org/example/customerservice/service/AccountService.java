package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.AccountRequest;
import org.example.customerservice.dto.AccountResponse;
import org.example.customerservice.entity.Account;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.repository.AccountRepository;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Créer un compte
    public AccountResponse createAccount(AccountRequest request) {
        // Vérifier que le client existe
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + request.getCustomerId()));

        // Vérifier que le username est unique
        if (accountRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists: " + request.getUsername());
        }

        // Générer un code de compte unique
        String accountCode = generateAccountCode();

        Account account = Account.builder()
                .customerId(request.getCustomerId())
                .accountCode(accountCode)
                .accountType(request.getAccountType())
                .balance(request.getBalance() != null ? request.getBalance() : BigDecimal.ZERO)
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .build();

        accountRepository.save(account);

        return toResponse(account, customer);
    }

    // Récupérer tous les comptes
    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(account -> {
                    Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
                    return toResponse(account, customer);
                })
                .collect(Collectors.toList());
    }

    // Récupérer un compte par ID
    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));
        Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
        return toResponse(account, customer);
    }

    // Récupérer les comptes d'un client
    @Transactional(readOnly = true)
    public List<AccountResponse> getAccountsByCustomer(Long customerId) {
        return accountRepository.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
                .map(account -> {
                    Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
                    return toResponse(account, customer);
                })
                .collect(Collectors.toList());
    }

    // Créditer un compte
    public AccountResponse creditAccount(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
        return toResponse(account, customer);
    }

    // Débiter un compte
    public AccountResponse debitAccount(Long id, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Amount must be positive");
        }

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
        return toResponse(account, customer);
    }

    // Activer/Désactiver un compte
    public AccountResponse toggleAccountStatus(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found: " + id));

        account.setIsActive(!account.getIsActive());
        accountRepository.save(account);

        Customer customer = customerRepository.findById(account.getCustomerId()).orElse(null);
        return toResponse(account, customer);
    }

    // Supprimer un compte
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    // Générer un code de compte unique
    private String generateAccountCode() {
        String code;
        do {
            code = "ACC-" + (System.currentTimeMillis() % 1000000);
        } while (accountRepository.existsByAccountCode(code));
        return code;
    }

    // Mapper vers Response DTO
    private AccountResponse toResponse(Account account, Customer customer) {
        return new AccountResponse(
                account.getAccountId(),
                account.getCustomerId(),
                customer != null ? customer.getFullName() : "Inconnu",
                account.getAccountCode(),
                account.getAccountType(),
                account.getBalance(),
                account.getCreatedAt(),
                account.getIsActive(),
                account.getUsername()
        );
    }
}