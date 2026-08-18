package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.serialnumber.SerialNumberRequestDto;
import com.tienda.zely.dto.serialnumber.SerialNumberResponseDto;
import com.tienda.zely.service.SerialNumberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/serial-number")
public class SerialNumberController {

    private final SerialNumberService serialNumberService;

    @PostMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SerialNumberResponseDto> registerSerialNumber(
            @RequestBody @Valid SerialNumberRequestDto serialNumberRequestDto) {
        log.info("Iniciando registro de numero de serie para el producto ID: {}",
                serialNumberRequestDto.getCodigoProducto());
        SerialNumberResponseDto response = serialNumberService.registerSerialNumber(serialNumberRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SerialNumberResponseDto> getBySerialNumber(
            @RequestParam @NotBlank(message = "El numero de serie es obligatorio") String serialNumber) {
        log.info("Buscando numero de serie: {}", serialNumber);
        SerialNumberResponseDto response = serialNumberService.findBySerialNumber(serialNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/product", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SerialNumberResponseDto>> getAllByProduct(
            @RequestParam @NotNull(message = "El id de producto es obligatorio") Integer idProduct) {
        log.info("Consultando numeros de serie. Filtro idProduct: {}", idProduct);
        List<SerialNumberResponseDto> response = serialNumberService.findAllByIdProduct(idProduct);
        return ResponseEntity.ok(response);
    }
}
