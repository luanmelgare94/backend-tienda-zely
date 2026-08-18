package com.tienda.zely.mapper;

import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.dto.person.PersonRequestDto;
import com.tienda.zely.dto.person.PersonResponseDto;
import com.tienda.zely.dto.person.PersonUpdateDto;
import com.tienda.zely.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "personRequestDto.nombreCompleto", target = "fullName")
    @Mapping(source = "personRequestDto.observacion", target = "observation")
    @Mapping(source = "personRequestDto.cuenta", target = "hasAccount")
    @Mapping(source = "personRequestDto.limiteCuenta", target = "accountLimit")
    @Mapping(constant = "true", target = "active")
    @Mapping(source = "now", target = "dateRegister")
    PersonEntity mapPersonRequestDtoToPersonEntity(PersonRequestDto personRequestDto, LocalDateTime now);

    @Mapping(source = "personEntity.fullName", target = "nombreCompleto")
    PersonResponseDto mapPersonEntityToPersonResponseDto(PersonEntity personEntity);

    @Mapping(source = "personEntity.idPerson", target = "codigoPersona")
    @Mapping(source = "personEntity.fullName", target = "nombreCompleto")
    @Mapping(source = "personEntity.observation", target = "observacion")
    @Mapping(source = "personEntity.hasAccount", target = "cuenta")
    @Mapping(source = "personEntity.accountLimit", target = "limiteCuenta")
    PersonDefaultDto mapPersonEntityToPersonDefaultDto(PersonEntity personEntity);

    List<PersonDefaultDto> mapListPersonEntityToPersonDefaultDto(List<PersonEntity> personEntities);

    @Mapping(source = "id", target = "idPerson")
    @Mapping(source = "updateDto.nombreCompleto", target = "fullName")
    @Mapping(source = "updateDto.observacion", target = "observation")
    @Mapping(source = "updateDto.cuenta", target = "hasAccount")
    @Mapping(source = "updateDto.limiteCuenta", target = "accountLimit")
    PersonEntity mapPersonUpdateDtoToPersonEntity(PersonUpdateDto updateDto, Integer id);

}