package org.example.customerservice.dto;
import lombok.Data;

@Data
public class TelecomServiceResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
