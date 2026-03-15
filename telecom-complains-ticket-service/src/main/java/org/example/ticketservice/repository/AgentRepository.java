package org.example.ticketservice.repository;

import org.example.ticketservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
    Optional<Agent> findByEmail(String email);
}