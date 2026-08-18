package com.tienda.zely.service.impl;

import com.tienda.zely.dto.typeproduct.TypeProductDefaultDto;
import com.tienda.zely.entity.TypeProductEntity;
import com.tienda.zely.mapper.TypeProductMapper;
import com.tienda.zely.repository.TypeProductRepository;
import com.tienda.zely.service.TypeProductService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TypeProductServiceImpl implements TypeProductService {

    private final TypeProductRepository typeProductRepository;
    private final TypeProductMapper typeProductMapper;

    @Override
    public List<TypeProductDefaultDto> getAllActiveTypeProducts() {
        return typeProductMapper.mapListTypeProductEntityToTypeProductDefaultDto(getAllTypeProductEntityActivated());
    }

    @Override
    public List<TypeProductEntity> getAllTypeProductEntityActivated() {
        log.info("Consultando tipos de producto activos");
        return typeProductRepository.findAll()
                .stream()
                .filter(TypeProductEntity::isActive)
                .sorted(Comparator.comparing(TypeProductEntity::getTypeProduct))
                .collect(Collectors.toList());
    }
}
