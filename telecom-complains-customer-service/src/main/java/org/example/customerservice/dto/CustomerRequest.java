package org.example.customerservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequest {
    @NotBlank(message = "Full name is required")
    private String fullName;
    @NotBlank @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Phone is required")
    private String phone;
    private String address;
}
