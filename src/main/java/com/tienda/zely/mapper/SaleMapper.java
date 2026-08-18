package com.tienda.zely.mapper;

import static com.tienda.zely.util.Constants.TIME_ZONE;

import com.tienda.zely.dto.sale.SaleDefaultDto;
import com.tienda.zely.dto.sale.SaleRequestDto;
import com.tienda.zely.dto.sale.SaleResponseByIdDto;
import com.tienda.zely.entity.DetailSaleEntity;
import com.tienda.zely.entity.MeasurementUnityEntity;
import com.tienda.zely.entity.SaleEntity;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "saleEntity", target = "saleEntity")
    @Mapping(source = "detailSaleRequestDto.producto.codigo", target = "productEntity.idProduct")
    @Mapping(source = "detailSaleRequestDto.cantidad", target = "quantity")
    @Mapping(source = "measurementUnityEntity", target = "measurementUnityEntity")
    @Mapping(source = "detailSaleRequestDto.precio", target = "price")
    @Mapping(source = "detailSaleRequestDto.observacion", target = "observation")
    @Mapping(constant = "true", target = "active")
    @Mapping(source = "now", target = "dateRegister")
    DetailSaleEntity mapDetailSaleRequestDtoToDetailSaleEntity(SaleRequestDto.DetailSaleRequestDto detailSaleRequestDto,
                                                               LocalDateTime now, SaleEntity saleEntity,
                                                               MeasurementUnityEntity measurementUnityEntity);

    @Mapping(source = "saleRequestDto.persona.codigo", target = "personEntity.idPerson")
    @Mapping(source = "saleRequestDto.tipoVenta.codigo", target = "typeSaleEntity.idTypeSale")
    @Mapping(source = "now", target = "dateRegister")
    @Mapping(source = "saleRequestDto.pagado", target = "paid")
    @Mapping(source = "saleRequestDto.observacion", target = "observation")
    @Mapping(constant = "true", target = "active")
    SaleEntity mapSaleRequestDtoToSaleEntitySimple(SaleRequestDto saleRequestDto, LocalDateTime now);

    default SaleEntity mapSaleRequestDtoToSaleEntity(SaleRequestDto saleRequestDto, List<MeasurementUnityEntity> entities) {
        SaleEntity saleEntity = mapSaleRequestDtoToSaleEntitySimple(saleRequestDto, LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        List<DetailSaleEntity> detailSaleEntityList = new ArrayList<>();
        List<SaleRequestDto.DetailSaleRequestDto> detalleVenta = saleRequestDto.getDetalleVenta();
        for (int i = 0; i < detalleVenta.size(); i++) {
            detailSaleEntityList.add(mapDetailSaleRequestDtoToDetailSaleEntity(
                    detalleVenta.get(i), LocalDateTime.now(ZoneId.of(TIME_ZONE)), saleEntity, entities.get(i)));
        }
        saleEntity.setDetailSaleEntityList(detailSaleEntityList);
        return saleEntity;
    }

    @Mapping(source = "detailSaleEntity.productEntity.idProduct", target = "producto.codigo")
    @Mapping(source = "detailSaleEntity.productEntity.name", target = "producto.nombre")
    @Mapping(source = "detailSaleEntity.quantity", target = "cantidad")
    @Mapping(source = "detailSaleEntity.measurementUnityEntity.idFather", target = "unidad.codigoPadre")
    @Mapping(source = "detailSaleEntity.measurementUnityEntity.value", target = "unidad.codigoValor")
    @Mapping(source = "detailSaleEntity.measurementUnityEntity.unity", target = "unidad.nombre")
    @Mapping(source = "detailSaleEntity.price", target = "precio")
    @Mapping(source = "detailSaleEntity.observation", target = "observacion")
    SaleResponseByIdDto.DetailSaleResponseByIdDto mapDetailSaleEntityToDetailSaleResponseByIdDto(DetailSaleEntity detailSaleEntity);

    @Mapping(source = "saleEntity.idSale", target = "codigo")
    @Mapping(source = "saleEntity.personEntity.idPerson", target = "persona.codigo")
    @Mapping(source = "saleEntity.personEntity.fullName", target = "persona.nombreCompleto")
    @Mapping(source = "saleEntity.typeSaleEntity.idTypeSale", target = "tipoVenta.codigo")
    @Mapping(source = "saleEntity.typeSaleEntity.typeSale", target = "tipoVenta.iso")
    @Mapping(source = "saleEntity.dateRegister", target = "fechaVenta")
    @Mapping(source = "saleEntity.paid", target = "pagado")
    @Mapping(source = "saleEntity.datePaid", target = "fechaPago")
    @Mapping(source = "saleEntity.observation", target = "observacion")
    @Mapping(source = "detailSaleResponseByIdDtoList", target = "detalleVenta")
    SaleResponseByIdDto mapSaleEntityToSaleResponseByIdDtoSimple(SaleEntity saleEntity, List<SaleResponseByIdDto.DetailSaleResponseByIdDto> detailSaleResponseByIdDtoList);

    default SaleResponseByIdDto mapSaleEntityToSaleResponseByIdDto(SaleEntity saleEntity) {
        List<SaleResponseByIdDto.DetailSaleResponseByIdDto> detailSaleResponseByIdDtoList = new ArrayList<>();
        for (DetailSaleEntity detailSaleEntity : saleEntity.getDetailSaleEntityList() ) {
            detailSaleResponseByIdDtoList.add(mapDetailSaleEntityToDetailSaleResponseByIdDto(detailSaleEntity));
        }
        return mapSaleEntityToSaleResponseByIdDtoSimple(saleEntity, detailSaleResponseByIdDtoList);
    }

    @Mapping(source = "saleEntity.idSale", target = "codigo")
    @Mapping(source = "saleEntity.personEntity.idPerson", target = "persona.codigo")
    @Mapping(source = "saleEntity.personEntity.fullName", target = "persona.nombreCompleto")
    @Mapping(source = "saleEntity.typeSaleEntity.idTypeSale", target = "tipoVenta.codigo")
    @Mapping(source = "saleEntity.typeSaleEntity.typeSale", target = "tipoVenta.iso")
    @Mapping(source = "saleEntity.dateRegister", target = "fechaVenta")
    @Mapping(source = "saleEntity.paid", target = "pagado")
    @Mapping(source = "saleEntity.datePaid", target = "fechaPago")
    @Mapping(source = "saleEntity.observation", target = "observacion")
    SaleDefaultDto mapSaleEntityToSaleDefaultDtoSimple(SaleEntity saleEntity);

    default List<SaleDefaultDto> mapListSaleEntityToSaleDefaultDto(List<SaleEntity> saleEntityList) {
        return saleEntityList.stream().map(this::mapSaleEntityToSaleDefaultDtoSimple).collect(Collectors.toList());
    }

}