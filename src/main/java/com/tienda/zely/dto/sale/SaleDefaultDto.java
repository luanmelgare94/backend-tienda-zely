package com.tienda.zely.dto.sale;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleDefaultDto {

    private Integer codigo;
    private PersonDefaultDto persona;
    private TypeSaleDefaultDto tipoVenta;
    private boolean pagado;
    private String observacion;
    private LocalDateTime fechaVenta;
    private LocalDateTime fechaPago;

    @Getter
    @Setter
    public static class PersonDefaultDto {
        private Integer codigo;
        private String nombreCompleto;
    }

    @Getter
    @Setter
    public static class TypeSaleDefaultDto {
        private Integer codigo;
        private String iso;
    }

}