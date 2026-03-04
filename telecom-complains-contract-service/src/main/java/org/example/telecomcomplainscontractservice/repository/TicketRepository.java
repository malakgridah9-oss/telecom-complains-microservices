package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}