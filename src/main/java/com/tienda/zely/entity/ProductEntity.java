package com.tienda.zely.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(schema = "schZely", name = "producto")
@Entity
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProducto")
    private Integer idProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_producto", nullable = false, foreignKey = @ForeignKey(name = "FK_producto_tipo_producto"))
    private TypeProductEntity typeProductEntity;

    @Column(name="nombre", nullable = false, length = 255, unique = true)
    private String name;

    @Column(name = "precio", nullable = false, columnDefinition = "numeric(6,2)")
    private double price;

    @Column(name = "observacion", length = 100)
    private String observation;

    @Column(name = "activo")
    private boolean active;

    @Column(name = "fechaRegistro")
    private LocalDateTime dateRegister;

    @Column(name = "fechaActualizacion")
    private LocalDateTime dateLastUpdate;

    @OneToMany(mappedBy = "productEntity", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private List<SerialNumberEntity> serialNumberEntities;

}
