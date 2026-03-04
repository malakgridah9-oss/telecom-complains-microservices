package org.example.telecomcomplainscontractservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "contract", schema = "telecom_ticketing")
public class Contract {
    @Id
    @Column(name = "contract_id", nullable = false)
    private Integer id;

    @Column(name = "customer_id")
    private Integer customerId;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "msisdn", length = 20)
    private String msisdn;

    @Column(name = "start_date")
    private LocalDate startDate;

    @jakarta.validation.constraints.Size(max = 20)
    @Column(name = "status", length = 20)
    private String status;


}