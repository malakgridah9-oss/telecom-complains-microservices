package org.example.ticketservice.repository;

import org.example.ticketservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByCustomerId(Integer customerId);
    List<Ticket> findByAssignedAgentId(Integer agentId);
    List<Ticket> findByStatus(String status);
    List<Ticket> findByCustomerIdAndStatus(
            Integer customerId, String status);
}