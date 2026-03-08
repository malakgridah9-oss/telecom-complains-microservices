package org.example.customerservice.service;
import org.example.customerservice.dto.*;
import java.util.List;

public interface ContractService {
    ContractResponse create(ContractRequest req);
    ContractResponse getById(Long id);
    List<ContractResponse> getAll();
    ContractResponse update(Long id, ContractRequest req);
    void delete(Long id);
    List<ContractResponse> getByCustomerId(Long customerId);
}
