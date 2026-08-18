package com.tienda.zely.dto.person;

import lombok.Data;

@Data
public class PersonDefaultDto {

    private String codigoPersona;

    private String nombreCompleto;

    private String observacion;

    private boolean cuenta;

    private double limiteCuenta;

}