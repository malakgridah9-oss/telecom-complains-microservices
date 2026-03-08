package org.example.ticketservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.ticketservice.entity.TicketPriority;
@Data
public class TicketRequest {
    @NotBlank(message="Title is required")
    private String title;
    @NotBlank(message="Description is required")
    private String description;
    @NotNull(message="Priority is required")
    private TicketPriority priority;
    @NotNull(message="Customer ID is required")
    private Long customerId;
    private Long agentId;
    private String category;
    private String resolutionNote;
}
