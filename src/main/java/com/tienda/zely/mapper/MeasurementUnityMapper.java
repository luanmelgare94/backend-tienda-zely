package com.tienda.zely.mapper;

import com.tienda.zely.dto.measurementunity.MeasurementUnityDefaultDto;
import com.tienda.zely.dto.measurementunity.MeasurementUnityResponseById;
import com.tienda.zely.entity.MeasurementUnityEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MeasurementUnityMapper {

    @Mapping(source = "value", target = "codigoValor")
    @Mapping(source = "unity", target = "nombre")
    MeasurementUnityDefaultDto mapMeasurementUnityEntityToMeasurementUnityDefaultDto(
            MeasurementUnityEntity measurementUnityEntity);

    List<MeasurementUnityDefaultDto> mapListMeasurementUnityEntityToMeasurementUnityDefaultDto(
            List<MeasurementUnityEntity> measurementUnityEntities);

    @Mapping(source = "idFather", target = "codigoPadre")
    @Mapping(source = "value", target = "codigoValor")
    @Mapping(source = "unity", target = "nombre")
    MeasurementUnityResponseById mapMeasurementUnityEntityToMeasurementUnityResponseById(
            MeasurementUnityEntity measurementUnityEntity);

    List<MeasurementUnityResponseById> mapListMeasurementUnityEntityToMeasurementUnityResponseById(
            List<MeasurementUnityEntity> measurementUnityEntities);

}