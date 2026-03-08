package org.example.ticketservice.repository;
import org.example.ticketservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCustomerId(Long customerId);
    List<Ticket> findByAgentId(Long agentId);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByPriority(TicketPriority priority);
    List<Ticket> findByCategory(String category);
    List<Ticket> findByCustomerIdAndStatus(Long customerId, TicketStatus status);
    long countByStatus(TicketStatus status);
    @Query("SELECT t FROM Ticket t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%',:kw,'%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%',:kw,'%'))")
    List<Ticket> searchByKeyword(String kw);
}
