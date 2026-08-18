package com.tienda.zely.dto.person;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonRequestDto {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(max = 100, message = "El nombre completo no puede superar 100 caracteres")
    private String nombreCompleto;

    @Size(max = 100, message = "La observacion no puede superar 100 caracteres")
    private String observacion;

    private boolean cuenta;

    @DecimalMin(value = "0.0", message = "El limite de cuenta no puede ser negativo")
    private double limiteCuenta;
}
