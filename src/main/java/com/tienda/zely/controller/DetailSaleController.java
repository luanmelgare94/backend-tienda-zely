package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.detailsale.DetailSaleSpecial;
import com.tienda.zely.service.DetailSaleService;
import jakarta.validation.constraints.NotNull;
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
@RequestMapping("/detailSale")
public class DetailSaleController {

    private final DetailSaleService detailSaleService;

    @GetMapping(path = "/detailSaleByCodigoCliente", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DetailSaleSpecial> getAllDetailSaleByIdPerson(
            @RequestParam @NotNull(message = "El codigo de cliente es obligatorio") Integer codigo) {
        log.info("Consultando detalles de venta por pagar para cliente ID: {}", codigo);
        return detailSaleService.getDetailSalesToPayByPerson(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}
