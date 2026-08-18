package com.tienda.zely.dto.parameter;

import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.dto.product.ProductDefaultDto;
import com.tienda.zely.dto.typesale.TypeSaleDefaultDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ParameterSaleDto {

    private List<PersonDefaultDto> personas;

    private List<TypeSaleDefaultDto> tipoVentas;

    private List<ProductDefaultDto> productos;

}