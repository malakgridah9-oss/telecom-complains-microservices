package org.example.telecomcomplainscontractservice.dto;

import lombok.Data;

@Data
public class ServiceResponse {
    private Integer serviceId;
    private String serviceCode;
    private String serviceName;
    private String serviceType;
    private String status;
    private String activationDate;
}