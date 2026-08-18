package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;

import com.tienda.zely.dto.typeproduct.TypeProductDefaultDto;
import com.tienda.zely.service.TypeProductService;
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
@RequestMapping(path = "/typeProduct")
public class TypeProductController {

    private final TypeProductService typeProductService;

    @GetMapping(path = "/getAll", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<TypeProductDefaultDto>> getAllTypeProductActivated() {
        log.info("Consultando tipos de producto activos");
        return ResponseEntity.ok(typeProductService.getAllActiveTypeProducts());
    }
}
