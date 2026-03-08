package org.example.customerservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class TelecomServiceRequest {
    @NotBlank(message = "Service name is required")
    private String name;
    private String description;
    private Double price;
}
