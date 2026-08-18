package com.tienda.zely.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "schZely", name = "detalleVenta")
public class DetailSaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalleVenta")
    private Integer idDetailSale;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idVenta", nullable = false, foreignKey = @ForeignKey(name = "FK_detail_sale_sale"))
    private SaleEntity saleEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false, foreignKey = @ForeignKey(name = "FK_detail_sale_product"))
    private ProductEntity productEntity;

    @Column(name = "cantidad", nullable = false, columnDefinition = "numeric(6,2)")
    private double quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUnidadMedicion", nullable = false, foreignKey = @ForeignKey(name = "FK_detail_sale_measurement_unity"))
    private MeasurementUnityEntity measurementUnityEntity;

    @Column(name = "precio", nullable = false, columnDefinition = "numeric(6,2)")
    private double price;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "activo")
    private boolean active;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

}