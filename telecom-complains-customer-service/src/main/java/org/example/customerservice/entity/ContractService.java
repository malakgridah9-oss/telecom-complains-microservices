package org.example.customerservice.entity;
import jakarta.persistence.*;
import lombok.*;

// TABLE: contract_service
// Liaison entre contract et service
// Ex: le contrat CTR-001 inclut les services Fibre + TV
@Entity
@Table(name = "contract_service")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ContractService {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private TelecomService telecomService;
}
