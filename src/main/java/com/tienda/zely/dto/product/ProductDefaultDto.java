package com.tienda.zely.dto.product;

import com.tienda.zely.dto.typeproduct.TypeProductDefaultDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ProductDefaultDto {
    private Integer codigo;

    private TypeProductDefaultDto tipoProducto;

    private String nombre;

    private double precio;

    private LocalDateTime ultimaActualizacion;

}
