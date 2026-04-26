package org.example.telecomcomplainsauthentservice.repository;

import org.example.telecomcomplainsauthentservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCustomerId(Integer customerId);

    // ✅ Même base telecom_ticketing — pas besoin de prefix
    @Modifying
    @Query(value = "UPDATE ticket SET assigned_agent_id = NULL, status = 'OPEN' WHERE assigned_agent_id = :agentId",
            nativeQuery = true)
    void unassignTicketsByAgentId(@Param("agentId") Integer agentId);
}