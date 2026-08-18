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
@Entity
@Table(schema = "schZely", name = "tipoVenta")
public class TypeSaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTipoVenta")
    private Integer idTypeSale;

    @Column(name="tipoVenta", nullable = false, length = 50, unique = true)
    private String typeSale;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "activo")
    private boolean active;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

}