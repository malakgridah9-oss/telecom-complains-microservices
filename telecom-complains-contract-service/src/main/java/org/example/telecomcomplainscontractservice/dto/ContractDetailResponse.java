package org.example.telecomcomplainscontractservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ContractDetailResponse {
    private Integer contractId;
    private String contractNumber;
    private Integer customerId;
    private String msisdn;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private CustomerResponse customer;
    private List<ServiceResponse> services;
}