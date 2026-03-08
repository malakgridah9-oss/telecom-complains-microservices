package org.example.customerservice.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "service")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TelecomService {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private Double price;

    @OneToMany(mappedBy = "telecomService", fetch = FetchType.LAZY)
    @ToString.Exclude @EqualsAndHashCode.Exclude
    private List<ContractService> contractServices;
}
