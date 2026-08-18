package com.tienda.zely.mapper;

import com.tienda.zely.dto.product.ProductDefaultDto;
import com.tienda.zely.dto.product.ProductRequestDto;
import com.tienda.zely.dto.product.ProductResponseDto;
import com.tienda.zely.dto.product.ProductUpdateDto;
import com.tienda.zely.entity.ProductEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "requestDto.codigoProducto", target = "idProduct")
    @Mapping(source = "requestDto.nombre", target = "name")
    @Mapping(source = "requestDto.codigoTipoProducto", target = "typeProductEntity.idTypeProduct")
    @Mapping(source = "requestDto.precio", target = "price")
    @Mapping(constant = "SIN OBSERVACION", target = "observation")
    @Mapping(constant = "true", target = "active")
    @Mapping(source = "now", target = "dateRegister")
    ProductEntity mapProductRequestDtoToProductEntity(ProductRequestDto requestDto, LocalDateTime now);

    default List<ProductEntity> mapListProductRequestDtoToProductEntity(List<ProductRequestDto> productRequestDtos,
                                                                        LocalDateTime now) {
        if ( productRequestDtos == null ) {
            return null;
        }
        List<ProductEntity> list = new ArrayList<>( productRequestDtos.size() );
        for ( ProductRequestDto productRequestDto : productRequestDtos ) {
            list.add( mapProductRequestDtoToProductEntity( productRequestDto, now ) );
        }
        return list;
    }

    @Mapping(source = "idProduct", target = "codigoProducto")
    @Mapping(source = "name", target = "nombre")
    @Mapping(source = "typeProductEntity.idTypeProduct", target = "codigoTipoProducto")
    @Mapping(source = "price", target = "precio")
    ProductResponseDto mapProductEntityToProductResponseDto(ProductEntity productEntity);

    List<ProductResponseDto> mapListProductEntityToProductResponseDto(List<ProductEntity> productEntities);

    @Mapping(source = "productEntity.idProduct", target = "codigo")
    @Mapping(source = "productEntity.typeProductEntity.idTypeProduct", target = "tipoProducto.codigo")
    @Mapping(source = "productEntity.typeProductEntity.typeProduct", target = "tipoProducto.nombre")
    @Mapping(source = "productEntity.name", target = "nombre")
    @Mapping(source = "productEntity.price", target = "precio")
    @Mapping(source = "productEntity.dateLastUpdate", target = "ultimaActualizacion")
    ProductDefaultDto mapProductEntityToProductDefaultDto(ProductEntity productEntity);

    List<ProductDefaultDto> mapListProductEntityToProductDefaultDto(List<ProductEntity> productEntities);

    @Mapping(source = "id", target = "idProduct")
    @Mapping(source = "productUpdateDto.nombre", target = "name")
    @Mapping(source = "productUpdateDto.codigoTipoProducto", target = "typeProductEntity.idTypeProduct")
    @Mapping(source = "productUpdateDto.precio", target = "price")
    ProductEntity mapProductUpdateDtoToProductEntity(ProductUpdateDto productUpdateDto, Integer id);
}