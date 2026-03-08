package org.example.customerservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AccountRequest {
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    private Boolean isActive;
}
