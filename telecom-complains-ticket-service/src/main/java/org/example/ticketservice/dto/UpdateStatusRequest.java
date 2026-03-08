package org.example.ticketservice.dto;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.ticketservice.entity.TicketStatus;
@Data
public class UpdateStatusRequest {
    @NotNull(message="Status is required")
    private TicketStatus status;
    private Long agentId;
    private String note;
}
