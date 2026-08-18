package com.tienda.zely.service.impl;

import com.tienda.zely.dto.typesale.TypeSaleDefaultDto;
import com.tienda.zely.entity.TypeSaleEntity;
import com.tienda.zely.mapper.TypeSaleMapper;
import com.tienda.zely.repository.TypeSaleRepository;
import com.tienda.zely.service.TypeSaleService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeSaleServiceImpl implements TypeSaleService {

    private final TypeSaleRepository typeSaleRepository;
    private final TypeSaleMapper typeSaleMapper;

    @Override
    public List<TypeSaleDefaultDto> getAllActiveTypeSales() {
        return typeSaleMapper.mapListTypeSaleEntityToTypeSaleDefaultDto(getAllTypeSaleEntityByActivated());
    }

    @Override
    public List<TypeSaleEntity> getAllTypeSaleEntityByActivated() {
        log.info("Consultando tipos de venta activos");
        return typeSaleRepository.findAll()
                .stream()
                .filter(TypeSaleEntity::isActive)
                .sorted(Comparator.comparing(TypeSaleEntity::getTypeSale))
                .collect(Collectors.toList());
    }
}
