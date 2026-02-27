package org.example.telecomcomplainscomplaintservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "reclamation")
@Data
public class Reclamation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String statut = "OPEN";

    @Column(name = "type_reclamation")
    private String typeReclamation;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "date_mise_a_jour")
    private LocalDateTime dateMiseAJour = LocalDateTime.now();

    @Column(name = "user_id", nullable = false)
    private Long userId;
}