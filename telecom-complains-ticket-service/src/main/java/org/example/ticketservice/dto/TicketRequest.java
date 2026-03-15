package org.example.ticketservice.dto;

import lombok.Data;

@Data
public class TicketRequest {
    private Integer customerId;
    private Integer contractId;
    private Integer contractServiceId;
    private String title;
    private String category;
    private String description;
    private String status;
}