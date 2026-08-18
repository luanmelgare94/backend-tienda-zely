package com.tienda.zely.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(schema = "schZely", name = "numeroSerie")
public class SerialNumberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idNumeroSerie")
    private Long idSerialNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false, foreignKey = @ForeignKey(name = "FK_serial_number_product"))
    private ProductEntity productEntity;

    @Column(name = "tieneNumeroSeriePropio")
    private boolean hasOwnSeriaNumber;

    @Column(name = "serialNumber", length = 50)
    private String serialNumber;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;
}
