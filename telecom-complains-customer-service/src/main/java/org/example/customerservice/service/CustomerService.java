package org.example.customerservice.service;

import org.example.customerservice.dto.*;
import java.util.List;

public interface CustomerService {

    // ─── CRUD ──────────────────────────────────────────────
    CustomerResponse create(CustomerRequest req);
    CustomerResponse getById(Long customerId);
    List<CustomerResponse> getAll();
    CustomerResponse update(Long customerId, CustomerRequest req);
    void delete(Long customerId);
    CustomerResponse getByEmail(String email);
    CustomerResponse getByPhone(String phone);

    // ─── MÉTHODES POUR CONTRATS (optionnel) ─────────────────
    List<CustomerResponse> getCustomersWithContracts();
    List<CustomerResponse> getCustomersWithoutContracts();
    boolean hasContract(Long customerId);
}