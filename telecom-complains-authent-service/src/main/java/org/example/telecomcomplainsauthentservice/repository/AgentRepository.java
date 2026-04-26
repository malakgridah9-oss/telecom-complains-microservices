package org.example.telecomcomplainsauthentservice.repository;

import org.example.telecomcomplainsauthentservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer> {
    boolean existsByEmail(String email);
    Optional<Agent> findByEmail(String email);
}