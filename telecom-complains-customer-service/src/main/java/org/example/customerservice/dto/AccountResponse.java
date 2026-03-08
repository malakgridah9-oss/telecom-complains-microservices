package org.example.customerservice.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AccountResponse {
    private Long id;
    private String username;
    private Boolean isActive;
    private Long customerId;
    private String customerFullName;
    private LocalDateTime createdAt;
}
