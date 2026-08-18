package com.tienda.zely.service;

import com.tienda.zely.dto.typeproduct.TypeProductDefaultDto;
import com.tienda.zely.entity.TypeProductEntity;
import java.util.List;

public interface TypeProductService {

    List<TypeProductDefaultDto> getAllActiveTypeProducts();

    List<TypeProductEntity> getAllTypeProductEntityActivated();
}
