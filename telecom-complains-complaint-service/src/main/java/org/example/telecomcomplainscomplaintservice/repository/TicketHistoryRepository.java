package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Integer> {
    List<TicketHistory> findByTicketId(Integer ticketId);
}