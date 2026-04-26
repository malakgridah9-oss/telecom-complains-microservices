package org.example.ticketservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class TicketHistoryResponse {
    private Integer historyId;
    private Integer ticketId;
    private String oldStatus;
    private String newStatus;
    private Integer changedByAgentId;
    private Instant changedAt;
    private String comment;
}