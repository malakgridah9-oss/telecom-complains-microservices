package org.example.ticketservice.service;

import org.example.ticketservice.dto.TicketHistoryResponse;
import org.example.ticketservice.dto.TicketRequest;
import org.example.ticketservice.dto.TicketResponse;
import java.util.List;

public interface TicketService {
    TicketResponse createTicket(TicketRequest request);
    TicketResponse getTicketById(Integer id);
    List<TicketResponse> getAllTickets();
    List<TicketResponse> getTicketsByCustomer(Integer customerId);
    List<TicketResponse> getTicketsByAgent(Integer agentId);
    List<TicketResponse> getTicketsByStatus(String status);
    TicketResponse assignAgent(Integer ticketId, Integer agentId);
    TicketResponse updateStatus(Integer ticketId,
                                String newStatus, Integer agentId, String comment);
    List<TicketHistoryResponse> getTicketHistory(Integer ticketId);
    void deleteTicket(Integer id);
}