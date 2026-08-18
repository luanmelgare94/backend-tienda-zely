package com.tienda.zely.service;

import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.dto.person.PersonRegisterResult;
import com.tienda.zely.dto.person.PersonRequestDto;
import com.tienda.zely.dto.person.PersonUpdateDto;
import com.tienda.zely.entity.PersonEntity;
import java.util.List;

public interface PersonService {

    PersonRegisterResult registerPerson(PersonRequestDto requestDto);

    List<PersonDefaultDto> getAllActivePersons();

    List<PersonDefaultDto> getAllInactivePersons();

    boolean updatePerson(PersonUpdateDto updateDto, Integer codigo);

    PersonEntity registerPersonEntity(PersonEntity personEntity);

    List<PersonEntity> getAllPersonEntityByActivated();

    List<PersonEntity> getAllPersonEntityByDeactivated();

    boolean enabledAccountOfPersonEntityById(Integer id);

    boolean disabledAccountOfPersonEntityById(Integer id);

    boolean existsPersonEntityByFullName(String name);

    PersonEntity getPersonEntityByFullName(String fullName);

    boolean deactivatePersonEntityById(Integer id);

    boolean activatePersonEntityById(Integer id);

    boolean updatePersonEntity(PersonEntity personEntity);

    List<PersonEntity> getAllPersonEntityWhenHasSaleWithoutPaid();
}
