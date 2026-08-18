package com.tienda.zely.mapper;

import com.tienda.zely.dto.typeproduct.TypeProductDefaultDto;
import com.tienda.zely.entity.TypeProductEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TypeProductMapper {

    @Mapping(source = "idTypeProduct", target = "codigo")
    @Mapping(source = "typeProduct", target = "nombre")
    TypeProductDefaultDto mapTypeProductEntityToTypeProductDefaultDto(TypeProductEntity typeProductEntity);

    List<TypeProductDefaultDto> mapListTypeProductEntityToTypeProductDefaultDto(List<TypeProductEntity> typeProductEntities);

}