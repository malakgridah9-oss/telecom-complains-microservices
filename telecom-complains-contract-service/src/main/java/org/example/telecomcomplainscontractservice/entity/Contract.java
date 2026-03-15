package org.example.telecomcomplainscontractservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Data
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    @JsonProperty("contractId")  // ← force l'affichage
    private Integer contractId;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "msisdn")
    private String msisdn;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "status")
    private String status;
}