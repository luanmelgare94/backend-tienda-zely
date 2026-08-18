package com.tienda.zely.mapper;

import com.tienda.zely.dto.serialnumber.SerialNumberRequestDto;
import com.tienda.zely.dto.serialnumber.SerialNumberResponseDto;
import com.tienda.zely.entity.ProductEntity;
import com.tienda.zely.entity.SerialNumberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface SerialNumberMapper {

    @Mapping(source = "serialNumberRequestDto.tieneCodigoPropio", target = "hasOwnSeriaNumber")
    @Mapping(source = "serialNumberRequestDto.numeroSerie", target = "serialNumber")
    @Mapping(source = "now", target = "dateRegister")
    SerialNumberEntity mapToSerialNumberEntity(SerialNumberRequestDto serialNumberRequestDto, LocalDateTime now, ProductEntity productEntity);

    @Mapping(source = "productEntity.name", target = "nombreProducto")
    @Mapping(source = "productEntity.price", target = "precio")
    @Mapping(source = "serialNumber", target = "numeroSerie")
    SerialNumberResponseDto mapToSerialNumberResponseDto(SerialNumberEntity serialNumberEntity);

    @Mapping(source = "productEntity.name", target = "nombreProducto")
    @Mapping(source = "serialNumber", target = "numeroSerie")
    SerialNumberResponseDto mapExceptPriceResponseDto(SerialNumberEntity serialNumberEntity);

    @Mapping(source = "serialNumber", target = "numeroSerie")
    SerialNumberResponseDto mapOnlySerialNumberToResponseDto(SerialNumberEntity serialNumberEntity);
}
