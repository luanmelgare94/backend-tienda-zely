package com.tienda.zely.dto.dashboard;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardDefaultDto {

    private Integer cantidadPersonas;

    private Integer cantidadProductos;

    private Long cantidadVentasEnDia;

    private Integer cantidadVentasPorPagar;

}