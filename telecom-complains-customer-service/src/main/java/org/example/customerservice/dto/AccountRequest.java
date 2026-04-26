package org.example.customerservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class AccountRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 255)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotBlank(message = "Account type is required")
    @Pattern(regexp = "PREPAID|POSTPAID", message = "Account type must be PREPAID or POSTPAID")
    private String accountType;

    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal balance;

    private Boolean isActive;
}