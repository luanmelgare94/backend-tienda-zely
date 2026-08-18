package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.parameter.ParameterProductAndUnityDto;
import com.tienda.zely.dto.parameter.ParameterSaleDto;
import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.service.ParameterService;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/parameter")
public class ParameterController {

    private final ParameterService parameterService;

    @GetMapping(path = "/parameter1", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ParameterSaleDto> getParameterForSale() {
        log.info("Consultando parametros para venta");
        return ResponseEntity.ok(parameterService.getSaleParameters());
    }

    @GetMapping(path = "/parameter2", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ParameterProductAndUnityDto> getParameterForMeasurementUnityByIdProduct(
            @RequestParam @NotNull(message = "El codigo de producto es obligatorio") Integer codigo) {
        log.info("Consultando parametros de unidad para producto ID: {}", codigo);
        return ResponseEntity.ok(parameterService.getProductAndUnityParameters(codigo));
    }

    @GetMapping(path = "/withoutPaid", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<PersonDefaultDto>> getAllPersonEntityWhenHasSaleWithoutPaid() {
        log.info("Consultando personas con ventas sin pagar");
        return parameterService.getPersonsWithUnpaidSales()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
