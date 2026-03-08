package org.example.ticketservice.dto;
import lombok.Data;
import java.time.LocalDate;
@Data
public class ContractResponse {
    private Long id;
    private String contractNumber;
    private Long customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isActive;
}
