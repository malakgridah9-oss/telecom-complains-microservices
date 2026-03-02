package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Integer> {
}