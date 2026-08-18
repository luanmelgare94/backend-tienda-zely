package com.tienda.zely.service.impl;

import static com.tienda.zely.util.Constants.TIME_ZONE;

import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.dto.person.PersonRegisterResult;
import com.tienda.zely.dto.person.PersonRequestDto;
import com.tienda.zely.dto.person.PersonUpdateDto;
import com.tienda.zely.entity.PersonEntity;
import com.tienda.zely.mapper.PersonMapper;
import com.tienda.zely.repository.PersonRepository;
import com.tienda.zely.service.PersonService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public PersonRegisterResult registerPerson(PersonRequestDto requestDto) {
        log.info("Registrando persona: {}", requestDto.getNombreCompleto());

        String normalizedName = requestDto.getNombreCompleto().toUpperCase();

        if (!personRepository.existsPersonEntityByFullName(normalizedName)) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
            PersonRequestDto normalizedRequest = buildNormalizedRequest(requestDto, normalizedName);
            PersonEntity saved = personRepository.save(
                    personMapper.mapPersonRequestDtoToPersonEntity(normalizedRequest, now));
            return PersonRegisterResult.created(personMapper.mapPersonEntityToPersonResponseDto(saved));
        }

        PersonEntity existing = personRepository.getByFullName(normalizedName);
        if (existing.isActive()) {
            return PersonRegisterResult.alreadyActive();
        }
        return PersonRegisterResult.inactiveExists();
    }

    @Override
    public List<PersonDefaultDto> getAllActivePersons() {
        return personMapper.mapListPersonEntityToPersonDefaultDto(getAllPersonEntityByActivated());
    }

    @Override
    public List<PersonDefaultDto> getAllInactivePersons() {
        return personMapper.mapListPersonEntityToPersonDefaultDto(getAllPersonEntityByDeactivated());
    }

    @Override
    @Transactional
    public boolean updatePerson(PersonUpdateDto updateDto, Integer codigo) {
        PersonUpdateDto normalizedUpdate = new PersonUpdateDto();
        normalizedUpdate.setNombreCompleto(updateDto.getNombreCompleto().toUpperCase());
        normalizedUpdate.setObservacion(updateDto.getObservacion());
        normalizedUpdate.setCuenta(updateDto.isCuenta());
        normalizedUpdate.setLimiteCuenta(updateDto.getLimiteCuenta());

        return updatePersonEntity(personMapper.mapPersonUpdateDtoToPersonEntity(normalizedUpdate, codigo));
    }

    @Override
    public PersonEntity registerPersonEntity(PersonEntity personEntity) {
        log.info("PersonServiceImpl.registerPersonEntity");
        return personRepository.save(personEntity);
    }

    @Override
    public List<PersonEntity> getAllPersonEntityByActivated() {
        log.info("Consultando personas activas");
        return personRepository.findAll()
                .stream()
                .filter(PersonEntity::isActive)
                .sorted(Comparator.comparing(PersonEntity::getFullName))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonEntity> getAllPersonEntityByDeactivated() {
        log.info("Consultando personas inactivas");
        return personRepository.findAll()
                .stream()
                .filter(personEntity -> !personEntity.isActive())
                .sorted(Comparator.comparing(PersonEntity::getFullName))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean enabledAccountOfPersonEntityById(Integer id) {
        log.info("Habilitando cuenta para persona ID: {}", id);
        if (!personRepository.existsById(id)) {
            return false;
        }
        return personRepository.updateHasAccountPersonEntityActiveByIdPerson(true, 10.0, id) == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean disabledAccountOfPersonEntityById(Integer id) {
        log.info("Deshabilitando cuenta para persona ID: {}", id);
        if (!personRepository.existsById(id)) {
            return false;
        }
        return personRepository.updateHasAccountPersonEntityActiveByIdPerson(false, 0.0, id) == 1;
    }

    @Override
    public boolean existsPersonEntityByFullName(String name) {
        log.info("PersonServiceImpl.existsPersonEntityByFullName.fullName: {}", name);
        return personRepository.existsPersonEntityByFullName(name);
    }

    @Override
    public PersonEntity getPersonEntityByFullName(String fullName) {
        log.info("PersonServiceImpl.getPersonEntityByFullName.fullName: {}", fullName);
        return personRepository.getByFullName(fullName);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deactivatePersonEntityById(Integer id) {
        log.info("Desactivando persona ID: {}", id);
        if (personRepository.existsById(id)) {
            return personRepository.updateActiveOfPersonEntityById(false, id) == 1;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean activatePersonEntityById(Integer id) {
        log.info("Activando persona ID: {}", id);
        if (personRepository.existsById(id)) {
            return personRepository.updateActiveOfPersonEntityById(true, id) == 1;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updatePersonEntity(PersonEntity personEntity) {
        log.info("Actualizando persona ID: {}", personEntity.getIdPerson());
        if (personRepository.existsById(personEntity.getIdPerson())) {
            return personRepository.updatePersonEntityFullNameAndObservationAndHasAccountAndAccountLimitByIdPerson(
                    personEntity.getFullName(), personEntity.getObservation(), personEntity.isHasAccount(),
                    personEntity.getAccountLimit(), personEntity.getIdPerson()) == 1;
        }
        return false;
    }

    @Override
    public List<PersonEntity> getAllPersonEntityWhenHasSaleWithoutPaid() {
        log.info("Consultando personas con ventas sin pagar");
        List<PersonEntity> personEntities = personRepository.getAllPersonEntityWhenHasSaleWithoutPaid();
        return personEntities.isEmpty() ? new ArrayList<>() : personEntities;
    }

    private PersonRequestDto buildNormalizedRequest(PersonRequestDto requestDto, String normalizedName) {
        PersonRequestDto normalizedRequest = new PersonRequestDto();
        normalizedRequest.setNombreCompleto(normalizedName);
        normalizedRequest.setObservacion(requestDto.getObservacion());
        normalizedRequest.setCuenta(requestDto.isCuenta());
        normalizedRequest.setLimiteCuenta(requestDto.getLimiteCuenta());
        return normalizedRequest;
    }
}
