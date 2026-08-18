package com.tienda.zely.mapper;

import com.tienda.zely.dto.typesale.TypeSaleDefaultDto;
import com.tienda.zely.entity.TypeSaleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeSaleMapper {

    @Mapping(source = "idTypeSale", target = "codigo")
    @Mapping(source = "typeSale", target = "nombre")
    TypeSaleDefaultDto mapTypeSaleEntityToTypeSaleDefaultDto(TypeSaleEntity saleEntity);

    List<TypeSaleDefaultDto> mapListTypeSaleEntityToTypeSaleDefaultDto(List<TypeSaleEntity> typeSaleEntities);

}