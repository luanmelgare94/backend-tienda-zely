package com.tienda.zely.controller;

import static com.tienda.zely.util.Constants.APPLICATION_JSON_UTF8_VALUE;
import static com.tienda.zely.util.Constants.APPLICATION_XLSX_VALUE;

import com.tienda.zely.dto.product.ProductDefaultDto;
import com.tienda.zely.dto.product.ProductRegisterResult;
import com.tienda.zely.dto.product.ProductRequestDto;
import com.tienda.zely.dto.product.ProductResponseDto;
import com.tienda.zely.dto.product.ProductUpdateDto;
import com.tienda.zely.service.ProductService;
import com.tienda.zely.util.ProductExcelHandler;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/active", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ProductDefaultDto>> getAllProductActivate() {
        log.info("Consultando productos activos");
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }

    @GetMapping(path = "/desactive", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ProductDefaultDto>> getAllProductDesactivate() {
        log.info("Consultando productos inactivos");
        return ResponseEntity.ok(productService.getAllInactiveProducts());
    }

    @GetMapping(path = "/downloadExcel", produces = APPLICATION_XLSX_VALUE)
    public void downloadProductsExcel(HttpServletResponse response) throws IOException {
        log.info("Exportando productos activos a Excel");
        response.setContentType(APPLICATION_XLSX_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=productos.xlsx");
        ProductExcelHandler.writeProducts(response.getOutputStream(), productService.getAllActiveProductsForExport());
    }

    @PostMapping(path = "/insert", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ProductResponseDto> insertProduct(@RequestBody @Valid ProductRequestDto productRequestDto) {
        log.info("Registrando producto: {}", productRequestDto.getNombre());
        ProductRegisterResult result = productService.registerProduct(productRequestDto);
        return switch (result.status()) {
            case CREATED -> ResponseEntity.status(HttpStatus.CREATED).body(result.product());
            case ALREADY_ACTIVE -> ResponseEntity.status(HttpStatus.ACCEPTED).build();
            case INACTIVE_EXISTS -> ResponseEntity.ok().build();
        };
    }

    @PostMapping(path = "/insertExcel")
    public ResponseEntity<Void> insertProductsByExcel(@RequestParam("file") MultipartFile file) {
        log.info("Importando productos desde Excel");
        productService.insertProductsFromExcel(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/update", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> updateProduct(
            @RequestParam @NotNull(message = "El codigo de producto es obligatorio") Integer codigoProducto,
            @RequestBody @Valid ProductUpdateDto productUpdateDto) {
        log.info("Actualizando producto ID: {}", codigoProducto);
        if (productService.updateProduct(productUpdateDto, codigoProducto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @PutMapping(path = "/active", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> activeProductById(
            @RequestParam @NotNull(message = "El codigo de producto es obligatorio") Integer codigoProducto) {
        log.info("Activando producto ID: {}", codigoProducto);
        if (productService.activateProductEntityById(codigoProducto)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }

    @PutMapping(path = "/updateExcel")
    public ResponseEntity<Void> updateProductsByExcel(@RequestParam("file") MultipartFile file) {
        log.info("Actualizando productos desde Excel");
        productService.updateProductsFromExcel(file);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/inactive/{id}", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Void> inactiveProductById(
            @PathVariable("id") @NotNull(message = "El id de producto es obligatorio") Integer id) {
        log.info("Desactivando producto ID: {}", id);
        if (productService.desactivateProductEntityById(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
    }
}
