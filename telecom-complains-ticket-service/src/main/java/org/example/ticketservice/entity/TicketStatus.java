package org.example.ticketservice.entity;

public enum TicketStatus {
    OPEN,        // Ticket ouvert, non traite
    IN_PROGRESS, // En cours de traitement par un agent
    RESOLVED,    // Resolu
    CLOSED,      // Ferme definitivement
    REJECTED     // Rejete
}
