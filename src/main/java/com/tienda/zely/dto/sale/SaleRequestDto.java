package com.tienda.zely.dto.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleRequestDto {

    @NotNull(message = "La persona es obligatoria")
    @Valid
    private PersonRequestDto persona;

    @NotNull(message = "El tipo de venta es obligatorio")
    @Valid
    private TypeSaleRequestDto tipoVenta;

    private boolean pagado;

    @Size(max = 100, message = "La observacion no puede superar 100 caracteres")
    private String observacion;

    @NotEmpty(message = "El detalle de venta no puede estar vacio")
    @Valid
    private List<DetailSaleRequestDto> detalleVenta;

    @Getter
    @Setter
    public static class PersonRequestDto {

        @NotNull(message = "El codigo de persona es obligatorio")
        @Positive(message = "El codigo de persona debe ser mayor a 0")
        private Integer codigo;
    }

    @Getter
    @Setter
    public static class TypeSaleRequestDto {

        @NotNull(message = "El codigo de tipo de venta es obligatorio")
        @Positive(message = "El codigo de tipo de venta debe ser mayor a 0")
        private Integer codigo;
    }

    @Getter
    @Setter
    public static class DetailSaleRequestDto {

        @NotNull(message = "El producto es obligatorio")
        @Valid
        private ProductRequestDto producto;

        @NotNull(message = "La cantidad es obligatoria")
        @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a 0")
        private Double cantidad;

        @NotNull(message = "La unidad de medida es obligatoria")
        @Valid
        private MeasurementUnityDto unidad;

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        private Double precio;

        @Size(max = 100, message = "La observacion no puede superar 100 caracteres")
        private String observacion;
    }

    @Getter
    @Setter
    public static class ProductRequestDto {

        @NotNull(message = "El codigo de producto es obligatorio")
        @Positive(message = "El codigo de producto debe ser mayor a 0")
        private Integer codigo;
    }

    @Getter
    @Setter
    public static class MeasurementUnityDto {

        @NotNull(message = "El codigo padre de la unidad es obligatorio")
        @Positive(message = "El codigo padre de la unidad debe ser mayor a 0")
        private Integer codigoPadre;

        @NotBlank(message = "El codigo valor de la unidad es obligatorio")
        private String codigoValor;
    }
}
