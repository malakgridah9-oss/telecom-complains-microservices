package org.example.ticketservice.dto;

import lombok.Data;

import java.time.Instant;


@Data
public class TicketResponse {
    private Integer ticketId;
    private Integer customerId;
    private Integer contractId;
    private Integer contractServiceId;
    private String title;
    private String category;
    private String description;
    private String status;
    private Integer assignedAgentId;
    private Instant createdAt;
}