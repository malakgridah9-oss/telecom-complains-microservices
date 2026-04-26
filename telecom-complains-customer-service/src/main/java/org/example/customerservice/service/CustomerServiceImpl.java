package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.exception.*;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Override
    public CustomerResponse create(CustomerRequest req) {
        if (repo.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email already exists: " + req.getEmail());

        Customer saved = repo.save(Customer.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        return map(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(Long customerId) {
        return map(find(customerId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse update(Long customerId, CustomerRequest req) {
        Customer c = find(customerId);
        if (!c.getEmail().equals(req.getEmail()) && repo.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email already exists: " + req.getEmail());
        c.setFullName(req.getFullName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setAddress(req.getAddress());
        c.setUpdatedAt(LocalDateTime.now());
        return map(repo.save(c));
    }

    @Override
    public void delete(Long customerId) {
        Customer customer = find(customerId);
        customer.setActive(false);  // ⭐ SOFT DELETE
        customer.setUpdatedAt(LocalDateTime.now());
        repo.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getByEmail(String email) {
        return map(repo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email)));
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getByPhone(String phone) {
        return map(repo.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with phone: " + phone)));
    }

    // ═══════════════════════════════════════════════════════════════
    // ⭐ MÉTHODES POUR CONTRATS - IMPLÉMENTATION CORRECTE
    // ═══════════════════════════════════════════════════════════════

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomersWithContracts() {
        return repo.findCustomersWithContracts().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomersWithoutContracts() {
        return repo.findCustomersWithoutContracts().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasContract(Long customerId) {
        return repo.hasContract(customerId);
    }

    // ═══════════════════════════════════════════════════════════════
    // PRIVATE METHODS
    // ═══════════════════════════════════════════════════════════════

    private Customer find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    private CustomerResponse map(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setCustomerId(c.getCustomerId());  // ⭐ Utiliser customerId, pas getId()
        r.setFullName(c.getFullName());
        r.setEmail(c.getEmail());
        r.setPhone(c.getPhone());
        r.setAddress(c.getAddress());
        r.setActive(c.isActive());
        r.setCreatedAt(c.getCreatedAt());
        r.setUpdatedAt(c.getUpdatedAt());
        return r;
    }
}