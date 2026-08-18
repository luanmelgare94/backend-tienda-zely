package com.tienda.zely.dto.serialnumber;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SerialNumberResponseDto {

    private String nombreProducto;
    private double precio;
    private String numeroSerie;
}
