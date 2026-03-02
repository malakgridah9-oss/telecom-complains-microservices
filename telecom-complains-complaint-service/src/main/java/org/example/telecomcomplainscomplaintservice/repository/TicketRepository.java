package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCustomerId(Integer customerId);
    List<Ticket> findByStatus(String status);
    List<Ticket> findByAssignedAgentId(Integer agentId);
}