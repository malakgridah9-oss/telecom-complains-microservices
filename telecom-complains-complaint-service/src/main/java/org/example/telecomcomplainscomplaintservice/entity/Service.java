package org.example.telecomcomplainscomplaintservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service", schema = "telecom_ticketing")
public class Service {
    @Id
    @Column(name = "service_id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "service_code", length = 50)
    private String serviceCode;

    @Size(max = 100)
    @Column(name = "service_name", length = 100)
    private String serviceName;

    @Size(max = 30)
    @Column(name = "service_type", length = 30)
    private String serviceType;


}