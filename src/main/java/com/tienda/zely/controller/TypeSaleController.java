package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.typesale.TypeSaleDefaultDto;
import com.tienda.zely.service.TypeSaleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/typeSale")
public class TypeSaleController {

    private final TypeSaleService typeSaleService;

    @GetMapping(path = "/getAll", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<TypeSaleDefaultDto>> getAllTypeSale() {
        log.info("Consultando tipos de venta activos");
        return ResponseEntity.ok(typeSaleService.getAllActiveTypeSales());
    }
}
