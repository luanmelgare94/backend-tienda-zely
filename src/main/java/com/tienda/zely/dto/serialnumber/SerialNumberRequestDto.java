package com.tienda.zely.dto.serialnumber;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class SerialNumberRequestDto {

    @NotNull(message = "El codigo de producto es obligatorio")
    private Integer codigoProducto;

    private boolean tieneCodigoPropio;

    @NotBlank(message = "El numero de serie es obligatorio")
    @Size(max = 50, message = "El numero de serie no puede superar 50 caracteres")
    private String numeroSerie;
}
