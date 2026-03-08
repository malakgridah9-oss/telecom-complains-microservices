package org.example.ticketservice.dto;
import lombok.Data;
import org.example.ticketservice.entity.TicketPriority;
import org.example.ticketservice.entity.TicketStatus;
import java.time.LocalDateTime;
@Data
public class TicketResponse {
    private Long id;
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private String category;
    private String resolutionNote;
    private Long customerId;
    private String customerFullName;
    private String customerEmail;
    private Long agentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime resolvedAt;
}
