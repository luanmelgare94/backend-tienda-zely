package com.tienda.zely.service;

import com.tienda.zely.dto.product.ProductDefaultDto;
import com.tienda.zely.dto.product.ProductRegisterResult;
import com.tienda.zely.dto.product.ProductRequestDto;
import com.tienda.zely.dto.product.ProductResponseDto;
import com.tienda.zely.dto.product.ProductUpdateDto;
import com.tienda.zely.entity.ProductEntity;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductRegisterResult registerProduct(ProductRequestDto productRequestDto);

    ProductEntity insertProductEntity(ProductEntity productEntity);

    boolean insertProductEntityCSV(List<ProductEntity> productEntities);

    boolean updateProductEntityCSV(List<ProductEntity> productEntities);

    void insertProductsFromCsv(MultipartFile file);

    void updateProductsFromCsv(MultipartFile file);

    List<ProductDefaultDto> getAllActiveProducts();

    List<ProductDefaultDto> getAllInactiveProducts();

    List<ProductResponseDto> getAllActiveProductsForExport();

    boolean updateProduct(ProductUpdateDto productUpdateDto, Integer codigoProducto);

    boolean activateProductEntityById(Integer id);

    boolean desactivateProductEntityById(Integer id);

    boolean existsProductEntityByName(String name);

    ProductEntity getProductEntityByName(String name);

    ProductEntity getProductEntityById(Integer id);

    ProductEntity getActiveProductOrThrow(Integer id);

    List<ProductEntity> getAllProductEntityActivate();

    List<ProductEntity> getAllProductEntityDeactivate();

    boolean updateProductEntity(ProductEntity productEntity);
}
