package org.example.telecomcomplainscomplaintservice.service;

import lombok.RequiredArgsConstructor;
import org.example.telecomcomplainscomplaintservice.entity.Reclamation;
import org.example.telecomcomplainscomplaintservice.repository.ReclamationRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReclamationService {

    private final ReclamationRepository reclamationRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Reclamation getReclamationById(Long id) {
        return reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réclamation non trouvée : " + id));
    }

    public Reclamation createReclamation(Reclamation reclamation) {
        reclamation.setStatut("OPEN");
        reclamation.setDateCreation(LocalDateTime.now());
        reclamation.setDateMiseAJour(LocalDateTime.now());
        return reclamationRepository.save(reclamation);
    }

    public Reclamation updateStatut(Long id, String statut) {
        Reclamation rec = getReclamationById(id);
        rec.setStatut(statut);
        rec.setDateMiseAJour(LocalDateTime.now());
        return reclamationRepository.save(rec);
    }

    public Reclamation updateReclamation(Long id, Reclamation reclamationDetails) {
        Reclamation rec = getReclamationById(id);
        rec.setTitre(reclamationDetails.getTitre());
        rec.setDescription(reclamationDetails.getDescription());
        rec.setTypeReclamation(reclamationDetails.getTypeReclamation());
        rec.setDateMiseAJour(LocalDateTime.now());
        return reclamationRepository.save(rec);
    }

    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    public List<Reclamation> getReclamationsByUser(Long userId) {
        return reclamationRepository.findByUserId(userId);
    }

    public List<Reclamation> getReclamationsByStatut(String statut) {
        return reclamationRepository.findByStatut(statut);
    }
}