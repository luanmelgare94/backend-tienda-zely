package com.tienda.zely.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(schema = "schZely", name = "persona")
@Entity
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPersona")
    private Integer idPerson;

    @Column(name = "nombreCompleto", nullable = false, length = 100)
    private String fullName;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "tieneCuenta")
    private boolean hasAccount;

    @Column(name = "limiteCuenta", columnDefinition = "numeric(6,2)")
    private double accountLimit;

    @Column(name = "activo")
    private boolean active;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

}