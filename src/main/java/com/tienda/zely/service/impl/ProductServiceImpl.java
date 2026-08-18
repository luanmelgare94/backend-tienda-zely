package com.tienda.zely.service.impl;

import static com.tienda.zely.util.Constants.SEPARATOR;
import static com.tienda.zely.util.Constants.TIME_ZONE;

import com.tienda.zely.dto.product.ProductDefaultDto;
import com.tienda.zely.dto.product.ProductRegisterResult;
import com.tienda.zely.dto.product.ProductRequestDto;
import com.tienda.zely.dto.product.ProductResponseDto;
import com.tienda.zely.dto.product.ProductUpdateDto;
import com.tienda.zely.entity.ProductEntity;
import com.tienda.zely.exception.ConflictException;
import com.tienda.zely.exception.ResourceNotFoundException;
import com.tienda.zely.mapper.ProductMapper;
import com.tienda.zely.repository.ProductRepository;
import com.tienda.zely.service.ProductService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductRegisterResult registerProduct(ProductRequestDto productRequestDto) {
        log.info("Registrando producto: {}", productRequestDto.getNombre());

        String normalizedName = productRequestDto.getNombre().toUpperCase();

        if (!productRepository.existsProductEntityByName(normalizedName)) {
            LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
            ProductRequestDto normalizedRequest = new ProductRequestDto(
                    normalizedName, productRequestDto.getCodigoTipoProducto(), productRequestDto.getPrecio());
            ProductEntity saved = productRepository.save(
                    productMapper.mapProductRequestDtoToProductEntity(normalizedRequest, now));
            return ProductRegisterResult.created(productMapper.mapProductEntityToProductResponseDto(saved));
        }

        ProductEntity existing = productRepository.getByName(normalizedName);
        if (existing.isActive()) {
            return ProductRegisterResult.alreadyActive();
        }
        return ProductRegisterResult.inactiveExists();
    }

    @Override
    public ProductEntity insertProductEntity(ProductEntity productEntity) {
        log.info("ProductServiceImpl.insertProductEntity");
        return productRepository.save(productEntity);
    }

    @Override
    public boolean insertProductEntityCSV(List<ProductEntity> productEntities) {
        log.info("ProductServiceImpl.insertProductEntityCSV");
        productRepository.saveAll(productEntities);
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateProductEntityCSV(List<ProductEntity> productEntities) {
        log.info("ProductServiceImpl.updateProductEntityCSV");
        for (ProductEntity product : productEntities) {
            productRepository.updateProductEntityByName(
                    product.getPrice(), product.getTypeProductEntity().getIdTypeProduct(),
                    LocalDateTime.now(ZoneId.of(TIME_ZONE)), product.getName(), product.getIdProduct());
        }
        return true;
    }

    @Override
    @Transactional
    public void insertProductsFromCsv(MultipartFile file) {
        log.info("Importando productos desde CSV");
        List<ProductRequestDto> products = parseProductsFromCsv(file, true);
        validateInsertProductsFromCsv(products);
        LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        insertProductEntityCSV(productMapper.mapListProductRequestDtoToProductEntity(products, now));
    }

    @Override
    @Transactional
    public void updateProductsFromCsv(MultipartFile file) {
        log.info("Actualizando productos desde CSV");
        List<ProductRequestDto> products = parseProductsFromCsv(file, false);
        validateUpdateProductsFromCsv(products);
        LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        updateProductEntityCSV(productMapper.mapListProductRequestDtoToProductEntity(products, now));
    }

    @Override
    public List<ProductDefaultDto> getAllActiveProducts() {
        return productMapper.mapListProductEntityToProductDefaultDto(getAllProductEntityActivate());
    }

    @Override
    public List<ProductDefaultDto> getAllInactiveProducts() {
        return productMapper.mapListProductEntityToProductDefaultDto(getAllProductEntityDeactivate());
    }

    @Override
    public List<ProductResponseDto> getAllActiveProductsForExport() {
        return productMapper.mapListProductEntityToProductResponseDto(getAllProductEntityActivate());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateProduct(ProductUpdateDto productUpdateDto, Integer codigoProducto) {
        ProductUpdateDto normalizedUpdate = new ProductUpdateDto(
                productUpdateDto.getNombre().toUpperCase(),
                productUpdateDto.getCodigoTipoProducto(),
                productUpdateDto.getPrecio());
        return updateProductEntity(productMapper.mapProductUpdateDtoToProductEntity(normalizedUpdate, codigoProducto));
    }

    @Override
    public List<ProductEntity> getAllProductEntityActivate() {
        log.info("ProductServiceImpl.getAllProductEntityActivate");
        return productRepository.findAllByActive(true);
    }

    @Override
    public List<ProductEntity> getAllProductEntityDeactivate() {
        log.info("ProductServiceImpl.getAllProductEntityDesactivate");
        return productRepository.findAllByActive(false)
                .stream()
                .sorted(Comparator.comparing(ProductEntity::getIdProduct))
                .collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateProductEntity(ProductEntity productEntity) {
        log.info("ProductServiceImpl.updateProductEntity");
        if (productRepository.existsById(productEntity.getIdProduct())) {
            productRepository.updateProductEntityByIdProduct(
                    productEntity.getName(), productEntity.getPrice(),
                    productEntity.getTypeProductEntity().getIdTypeProduct(), LocalDateTime.now(ZoneId.of(TIME_ZONE)),
                    productEntity.getIdProduct());
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean desactivateProductEntityById(Integer id) {
        log.info("Desactivando producto ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.updateDesactivateProductEntityActiveById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean activateProductEntityById(Integer id) {
        log.info("Activando producto ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.updateActivateProductEntityActiveById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean existsProductEntityByName(String name) {
        log.info("ProductServiceImpl.existsProductEntityByName.name: {}", name);
        return productRepository.existsProductEntityByName(name.toUpperCase());
    }

    @Override
    public ProductEntity getProductEntityByName(String name) {
        log.info("ProductServiceImpl.getProductEntityByName.name: {}", name);
        return productRepository.getByName(name);
    }

    @Override
    public ProductEntity getProductEntityById(Integer id) {
        log.info("ProductServiceImpl.getProductEntityById.id: {}", id);
        return productRepository.findById(id).orElse(new ProductEntity());
    }

    @Override
    public ProductEntity getActiveProductOrThrow(Integer id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con el ID: " + id));
        if (!product.isActive()) {
            throw new ConflictException("El producto no esta activo");
        }
        return product;
    }

    private void validateInsertProductsFromCsv(List<ProductRequestDto> products) {
        List<String> errors = new ArrayList<>();
        Set<String> namesInFile = new HashSet<>();

        for (int i = 0; i < products.size(); i++) {
            ProductRequestDto product = products.get(i);
            int row = i + 2;
            String name = product.getNombre();

            if (!namesInFile.add(name)) {
                errors.add("Fila " + row + ": el producto '" + name + "' esta duplicado en el archivo");
                continue;
            }

            if (!productRepository.existsProductEntityByName(name)) {
                continue;
            }

            ProductEntity existing = productRepository.getByName(name);
            if (existing.isActive()) {
                errors.add("Fila " + row + ": el producto '" + name + "' ya existe y esta activo");
            } else {
                errors.add("Fila " + row + ": el producto '" + name + "' existe pero esta inactivo");
            }
        }

        if (!errors.isEmpty()) {
            throw new ConflictException(String.join("; ", errors));
        }
    }

    private void validateUpdateProductsFromCsv(List<ProductRequestDto> products) {
        List<String> errors = new ArrayList<>();
        Set<Integer> idsInFile = new HashSet<>();

        for (int i = 0; i < products.size(); i++) {
            ProductRequestDto product = products.get(i);
            int row = i + 2;
            Integer productId = product.getCodigoProducto();

            if (!idsInFile.add(productId)) {
                errors.add("Fila " + row + ": el codigoProducto " + productId + " esta duplicado en el archivo");
                continue;
            }

            if (!productRepository.existsById(productId)) {
                errors.add("Fila " + row + ": producto no encontrado con el ID " + productId);
            }
        }

        if (!errors.isEmpty()) {
            throw new ConflictException(String.join("; ", errors));
        }
    }

    private List<ProductRequestDto> parseProductsFromCsv(MultipartFile file, boolean isInsert) {
        if (file == null || file.isEmpty()) {
            throw new ConflictException("El archivo CSV esta vacio o no fue enviado");
        }

        List<ProductRequestDto> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line = reader.readLine();
            int row = 0;
            while (line != null) {
                if (row > 0) {
                    String[] fields = line.split(SEPARATOR);
                    result.add(mapCsvRowToProductRequestDto(fields, isInsert));
                }
                line = reader.readLine();
                row++;
            }
        } catch (IOException e) {
            log.error("Error al leer archivo CSV de productos", e);
            throw new ConflictException("No se pudo procesar el archivo CSV");
        }

        if (result.isEmpty()) {
            throw new ConflictException("El archivo CSV no contiene productos para procesar");
        }
        return result;
    }

    private ProductRequestDto mapCsvRowToProductRequestDto(String[] fields, boolean isInsert) {
        if (isInsert) {
            validateColumnCount(fields, 3, "insert");
            return new ProductRequestDto(
                    fields[0].trim().toUpperCase(),
                    parsePositiveInt(fields[1], "codigoTipoProducto"),
                    parsePositiveDouble(fields[2], "precio"));
        }
        validateColumnCount(fields, 4, "update");
        ProductRequestDto dto = new ProductRequestDto(
                fields[1].trim().toUpperCase(),
                parsePositiveInt(fields[2], "codigoTipoProducto"),
                parsePositiveDouble(fields[3], "precio"));
        dto.setCodigoProducto(parsePositiveInt(fields[0], "codigoProducto"));
        return dto;
    }

    private void validateColumnCount(String[] fields, int expected, String operation) {
        if (fields.length < expected) {
            throw new ConflictException(
                    "Fila CSV invalida para " + operation + ": se esperaban " + expected + " columnas, se encontraron " + fields.length);
        }
    }

    private Integer parsePositiveInt(String value, String fieldName) {
        try {
            int parsed = Integer.parseInt(value.trim());
            if (parsed <= 0) {
                throw new ConflictException("El campo " + fieldName + " debe ser mayor a 0");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new ConflictException("El campo " + fieldName + " no es un numero valido: " + value);
        }
    }

    private double parsePositiveDouble(String value, String fieldName) {
        try {
            double parsed = Double.parseDouble(value.trim());
            if (parsed <= 0) {
                throw new ConflictException("El campo " + fieldName + " debe ser mayor a 0");
            }
            return parsed;
        } catch (NumberFormatException e) {
            throw new ConflictException("El campo " + fieldName + " no es un numero valido: " + value);
        }
    }
}
