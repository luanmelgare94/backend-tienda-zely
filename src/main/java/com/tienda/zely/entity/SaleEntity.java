package com.tienda.zely.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "schZely", name = "venta")
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta")
    private Integer idSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPersona", nullable = false, foreignKey = @ForeignKey(name = "FK_sale_person"))
    private PersonEntity personEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idTipoVenta", nullable = false, foreignKey = @ForeignKey(name = "FK_sale_type_sale"))
    private TypeSaleEntity typeSaleEntity;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

    @Column(name = "pagado")
    private boolean isPaid;

    @Column(name = "fechaPago")
    private LocalDateTime datePaid;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "activo")
    private boolean active;

    @OneToMany(mappedBy = "saleEntity", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<DetailSaleEntity> detailSaleEntityList;

}