package org.example.customerservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CustomerResponse {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}