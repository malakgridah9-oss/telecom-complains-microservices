package org.example.customerservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ContractRequest {
    @NotBlank(message = "Contract number is required")
    private String contractNumber;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
