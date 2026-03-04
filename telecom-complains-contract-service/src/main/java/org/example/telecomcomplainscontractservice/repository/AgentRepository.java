package org.example.telecomcomplainscontractservice.repository;

import org.example.telecomcomplainscontractservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Integer> {
}