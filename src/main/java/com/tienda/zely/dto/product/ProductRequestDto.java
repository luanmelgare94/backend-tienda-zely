package com.tienda.zely.dto.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequestDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer codigoProducto;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 255, message = "El nombre no puede superar 255 caracteres")
    private String nombre;

    @NotNull(message = "El codigo de tipo de producto es obligatorio")
    @Positive(message = "El codigo de tipo de producto debe ser mayor a 0")
    private Integer codigoTipoProducto;

    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private double precio;

    public ProductRequestDto(String nombre, Integer codigoTipoProducto, double precio) {
        this.nombre = nombre;
        this.codigoTipoProducto = codigoTipoProducto;
        this.precio = precio;
    }
}
