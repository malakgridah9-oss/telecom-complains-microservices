package org.example.ticketservice.repository;

import org.example.ticketservice.entity.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Integer> {
    List<TicketHistory> findByTicketIdOrderByChangedAtDesc(
            Integer ticketId);
}