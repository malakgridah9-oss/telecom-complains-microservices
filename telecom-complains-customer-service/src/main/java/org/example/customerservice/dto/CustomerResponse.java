package org.example.customerservice.dto;
import lombok.Data;

@Data
public class CustomerResponse {
    private Long customerId;    // customer_id
    private String fullName;    // full_name
    private String email;
    private String phone;
    private String address;
}
