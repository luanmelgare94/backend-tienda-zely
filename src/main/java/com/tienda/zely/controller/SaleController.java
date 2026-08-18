package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.sale.SaleDefaultDto;
import com.tienda.zely.dto.sale.SaleRequestDto;
import com.tienda.zely.dto.sale.SaleResponseByIdDto;
import com.tienda.zely.service.SaleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleController {

    private final SaleService saleService;

    @PostMapping(path = "/register", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> registerSale(@RequestBody @Valid SaleRequestDto saleRequestDto) {
        log.info("Registrando venta para persona ID: {}", saleRequestDto.getPersona().getCodigo());
        saleService.registerSale(saleRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path = "/getAll", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SaleDefaultDto>> getAllSaleEntityActivated() {
        log.info("Consultando ventas activas");
        return ResponseEntity.ok(saleService.getAllActiveSales());
    }

    @GetMapping(path = "/getById", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaleResponseByIdDto> getSaleEntityActivatedByIdSale(
            @RequestParam @NotNull(message = "El codigo de venta es obligatorio") Integer codigo) {
        log.info("Consultando venta ID: {}", codigo);
        return saleService.findSaleById(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/getPaid", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SaleDefaultDto>> getAllSaleEntityActivatedAndPaid() {
        log.info("Consultando ventas pagadas");
        return ResponseEntity.ok(saleService.getAllPaidAndActivatedSales());
    }

    @GetMapping(path = "/getPaidById", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SaleDefaultDto>> getAllSaleEntityActivatedAndPaidByIdPerson(
            @RequestParam @NotNull(message = "El codigo de persona es obligatorio") Integer codigo) {
        log.info("Consultando ventas pagadas para persona ID: {}", codigo);
        List<SaleDefaultDto> sales = saleService.getPaidAndActivatedSalesByPerson(codigo);
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sales);
    }

    @GetMapping(path = "/getNotPaid", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SaleDefaultDto>> getAllSaleEntityActivatedAndNotPaid() {
        log.info("Consultando ventas sin pagar");
        return ResponseEntity.ok(saleService.getAllNotPaidAndActivatedSales());
    }

    @GetMapping(path = "/getNotPaidById", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<SaleDefaultDto>> getAllSaleEntityActivatedAndNotPaidByIdPerson(
            @RequestParam @NotNull(message = "El codigo de persona es obligatorio") Integer codigo) {
        log.info("Consultando ventas sin pagar para persona ID: {}", codigo);
        List<SaleDefaultDto> sales = saleService.getNotPaidAndActivatedSalesByPerson(codigo);
        if (sales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(sales);
    }

    @PatchMapping(path = "/paidSale", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> paidSaleEntityByIdSale(
            @RequestParam @NotNull(message = "El codigo de venta es obligatorio") Integer codigo) {
        log.info("Marcando venta como pagada ID: {}", codigo);
        if (saleService.markSaleAsPaid(codigo)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }
}
