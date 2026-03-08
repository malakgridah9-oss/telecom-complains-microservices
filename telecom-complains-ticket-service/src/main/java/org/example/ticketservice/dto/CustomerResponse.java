package org.example.ticketservice.dto;
import lombok.Data;
@Data
public class CustomerResponse {
    private Long customerId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}
