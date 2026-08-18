package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.dto.person.PersonRegisterResult;
import com.tienda.zely.dto.person.PersonRequestDto;
import com.tienda.zely.dto.person.PersonResponseDto;
import com.tienda.zely.dto.person.PersonUpdateDto;
import com.tienda.zely.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/person")
public class PersonController {

    private final PersonService personService;

    @GetMapping(path = "/getAll", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PersonDefaultDto>> getAllPersonActivated() {
        log.info("Consultando personas activas");
        return ResponseEntity.ok(personService.getAllActivePersons());
    }

    @GetMapping(path = "/getDeactivated", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PersonDefaultDto>> getAllPersonDeactivated() {
        log.info("Consultando personas inactivas");
        return ResponseEntity.ok(personService.getAllInactivePersons());
    }

    @PostMapping(path = "/register", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PersonResponseDto> registerPerson(@RequestBody @Valid PersonRequestDto requestDto) {
        log.info("Registrando persona: {}", requestDto.getNombreCompleto());
        PersonRegisterResult result = personService.registerPerson(requestDto);
        return switch (result.status()) {
            case CREATED -> ResponseEntity.status(HttpStatus.CREATED).body(result.person());
            case ALREADY_ACTIVE -> ResponseEntity.status(HttpStatus.ACCEPTED).build();
            case INACTIVE_EXISTS -> ResponseEntity.ok().build();
        };
    }

    @PutMapping(path = "/update", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updatePersonEntity(
            @RequestParam @NotNull(message = "El codigo es obligatorio") Integer codigo,
            @RequestBody @Valid PersonUpdateDto updateDto) {
        log.info("Actualizando persona ID: {}", codigo);
        if (personService.updatePerson(updateDto, codigo)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @PatchMapping(path = "/enabledAccount", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> updateEnabledAccountForPersonEntityById(
            @RequestParam @NotNull(message = "El codigo es obligatorio") Integer codigo) {
        log.info("Habilitando cuenta para persona ID: {}", codigo);
        return ResponseEntity.ok(personService.enabledAccountOfPersonEntityById(codigo));
    }

    @PatchMapping(path = "/disabledAccount", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Boolean> updateDisabledAccountForPersonEntityById(
            @RequestParam @NotNull(message = "El codigo es obligatorio") Integer codigo) {
        log.info("Deshabilitando cuenta para persona ID: {}", codigo);
        return ResponseEntity.ok(personService.disabledAccountOfPersonEntityById(codigo));
    }

    @PatchMapping(path = "/active", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> activePersonEntityById(
            @RequestParam @NotNull(message = "El codigo es obligatorio") Integer codigo) {
        log.info("Activando persona ID: {}", codigo);
        if (personService.activatePersonEntityById(codigo)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @DeleteMapping(path = "/inactive", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> inactivePersonEntityById(
            @RequestParam @NotNull(message = "El codigo es obligatorio") Integer codigo) {
        log.info("Desactivando persona ID: {}", codigo);
        if (personService.deactivatePersonEntityById(codigo)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
