package org.example.telecomcomplainscontractservice.dto;

import lombok.Data;

@Data
public class CustomerResponse {
    private Integer customerId;
    private String fullName;
    private String email;
    private String phone;
    private String address;
}