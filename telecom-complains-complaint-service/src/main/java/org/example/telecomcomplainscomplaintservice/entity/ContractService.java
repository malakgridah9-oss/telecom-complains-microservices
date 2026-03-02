package org.example.telecomcomplainscomplaintservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contract_service", schema = "telecom_ticketing")
public class ContractService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_service_id", nullable = false)
    private Integer id;

    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "service_id")
    private Integer serviceId;

    @Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "activation_date")
    private LocalDate activationDate;


}