package org.example.customerservice.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ContractResponse {
    private Long id;
    private String contractNumber;
    private Long customerId;
    private String customerFullName;
    private String msisdn;          // ← vérifie que c'est là
    private LocalDate startDate;
    private LocalDate endDate;      // ← vérifie que c'est là
    private Boolean isActive;
    private LocalDateTime createdAt;
}