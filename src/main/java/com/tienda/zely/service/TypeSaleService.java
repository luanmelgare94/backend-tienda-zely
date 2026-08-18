package com.tienda.zely.service;

import com.tienda.zely.dto.typesale.TypeSaleDefaultDto;
import com.tienda.zely.entity.TypeSaleEntity;
import java.util.List;

public interface TypeSaleService {

    List<TypeSaleDefaultDto> getAllActiveTypeSales();

    List<TypeSaleEntity> getAllTypeSaleEntityByActivated();
}
