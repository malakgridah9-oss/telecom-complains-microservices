package org.example.telecomcomplainscomplaintservice.repository;

import org.example.telecomcomplainscomplaintservice.entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByUserId(Long userId);
    List<Reclamation> findByStatut(String statut);
}