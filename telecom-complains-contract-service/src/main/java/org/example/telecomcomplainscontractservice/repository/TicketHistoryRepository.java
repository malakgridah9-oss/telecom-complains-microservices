package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.TicketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketHistoryRepository extends JpaRepository<TicketHistory, Integer> {
}