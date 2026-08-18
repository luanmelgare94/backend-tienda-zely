package com.tienda.zely.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(schema = "schZely", name = "unidadMedicion")
public class MeasurementUnityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUnidadMedicion")
    private Integer idMeasurementUnity;

    @Column(name = "idPadre")
    private Integer idFather;

    @Column(name="unidad", nullable = false, length = 50, unique = true)
    private String unity;

    @Column(name = "valor", length = 3)
    private String value;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "activo")
    private boolean active;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

}