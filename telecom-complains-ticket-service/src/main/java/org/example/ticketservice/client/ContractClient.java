package org.example.ticketservice.client;

import org.example.ticketservice.dto.ContractResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "telecom-complains-contract-service")
public interface ContractClient {
    @GetMapping("/api/contracts/{id}")
    ContractResponse getContractById(@PathVariable Long id);

    @GetMapping("/api/contracts/customer/{customerId}")
    List<ContractResponse> getContractsByCustomer(@PathVariable Long customerId);
}
