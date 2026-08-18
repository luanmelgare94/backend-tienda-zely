package com.tienda.zely.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer codigoProducto;
    private String nombre;
    private Integer codigoTipoProducto;
    private double precio;
}
