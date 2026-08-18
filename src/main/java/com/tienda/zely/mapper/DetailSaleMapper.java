package com.tienda.zely.mapper;

import com.tienda.zely.dto.detailsale.DetailSaleSpecial;
import com.tienda.zely.entity.DetailSaleEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetailSaleMapper {

    @Mapping(source = "dateRegister", target = "fechaVenta")
    @Mapping(source = "quantity", target = "cantidad")
    @Mapping(source = "measurementUnityEntity.unity", target = "unidadMedida")
    @Mapping(source = "productEntity.name", target = "nombreProducto")
    @Mapping(source = "price", target = "precioVenta")
    DetailSaleSpecial.DetalleVenta mapDetailSaleEntityToDetalleVenta(DetailSaleEntity detailSaleEntity);

    List<DetailSaleSpecial.DetalleVenta> mapListDetailSaleEntityToDetalleVenta(List<DetailSaleEntity> list);

}