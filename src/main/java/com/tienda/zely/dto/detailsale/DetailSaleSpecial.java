package com.tienda.zely.dto.detailsale;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@Builder
public class DetailSaleSpecial {

    private Integer codigoCliente;

    private String nombreCliente;

    private List<DetalleVenta> detallesVenta;

    private Double precioFinal;

    @Getter
    @Setter
    public static class DetalleVenta {
        private LocalDateTime fechaVenta;
        private Integer cantidad;
        private String unidadMedida;
        private String nombreProducto;
        private String precioVenta;
    }

}