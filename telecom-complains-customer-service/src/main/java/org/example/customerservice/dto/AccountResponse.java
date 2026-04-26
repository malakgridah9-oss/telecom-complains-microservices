package org.example.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long accountId;
    private Long customerId;
    private String customerName;
    private String accountCode;
    private String accountType;
    private BigDecimal balance;
    private LocalDateTime createdAt;
    private Boolean isActive;
    private String username;
}