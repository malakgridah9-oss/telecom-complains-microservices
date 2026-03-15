package org.example.telecomcomplainscontractservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ContractDetailResponse {
    private Integer contractId;
    private String msisdn;
    private LocalDate startDate;
    private String status;

    // données customer venant de customer-service
    private CustomerResponse customer;

    // services liés au contrat
    private List<ServiceResponse> services;
}