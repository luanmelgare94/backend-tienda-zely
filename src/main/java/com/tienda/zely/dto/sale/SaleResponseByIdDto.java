package com.tienda.zely.dto.sale;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleResponseByIdDto {

    private Integer codigo;
    private PersonResponseByIdDto persona;
    private TypeSaleResponseByIdDto tipoVenta;
    private boolean pagado;
    private String observacion;
    private LocalDateTime fechaVenta;
    private LocalDateTime fechaPago;
    private List<DetailSaleResponseByIdDto> detalleVenta;

    @Getter
    @Setter
    public static class PersonResponseByIdDto {
        private Integer codigo;
        private String nombreCompleto;
    }

    @Getter
    @Setter
    public static class TypeSaleResponseByIdDto {
        private Integer codigo;
        private String iso;
    }

    @Getter
    @Setter
    public static class DetailSaleResponseByIdDto {
        private ProductResponseByIdDto producto;
        private Double cantidad;
        private MeasurementUnityDto unidad;
        private Double precio;
        private String observacion;
    }

    @Getter
    @Setter
    public static class ProductResponseByIdDto {
        private Integer codigo;
        private String nombre;
    }

    @Getter
    @Setter
    public static class MeasurementUnityDto {
        private Integer codigoPadre;
        private String codigoValor;
        private String nombre;
    }

}