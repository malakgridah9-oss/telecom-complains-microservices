package org.example.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.example.customerservice.dto.*;
import org.example.customerservice.entity.Customer;
import org.example.customerservice.exception.*;
import org.example.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repo;

    @Override
    public CustomerResponse create(CustomerRequest req) {
        // Verifie que l'email n'existe pas deja dans la table customer
        if (repo.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email already exists: " + req.getEmail());

        Customer saved = repo.save(Customer.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
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
        // Si email change -> verifie qu'il n'est pas deja pris
        if (!c.getEmail().equals(req.getEmail()) && repo.existsByEmail(req.getEmail()))
            throw new DuplicateResourceException("Email already exists: " + req.getEmail());

        // Met a jour les colonnes: full_name, email, phone, address
        c.setFullName(req.getFullName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setAddress(req.getAddress());
        return map(repo.save(c));
    }

    @Override
    public void delete(Long customerId) {
        repo.delete(find(customerId));
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

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> search(String keyword) {
        return repo.searchByKeyword(keyword).stream().map(this::map).collect(Collectors.toList());
    }

    // Cherche en base par customer_id, lance 404 si introuvable
    private Customer find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    // Entity -> DTO: mappe customer_id, full_name, email, phone, address
    private CustomerResponse map(Customer c) {
        CustomerResponse r = new CustomerResponse();
        r.setCustomerId(c.getCustomerId());
        r.setFullName(c.getFullName());
        r.setEmail(c.getEmail());
        r.setPhone(c.getPhone());
        r.setAddress(c.getAddress());
        return r;
    }
}
