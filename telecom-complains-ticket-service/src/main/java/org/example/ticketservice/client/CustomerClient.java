package org.example.ticketservice.client;

import org.example.ticketservice.dto.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "telecom-complains-customer-service")
public interface CustomerClient {
    @GetMapping("/api/customers/{customerId}")
    CustomerResponse getCustomerById(@PathVariable Long customerId);
}
