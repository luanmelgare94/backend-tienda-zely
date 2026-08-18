package com.tienda.zely.service.impl;

import com.tienda.zely.dto.parameter.ParameterProductAndUnityDto;
import com.tienda.zely.dto.parameter.ParameterSaleDto;
import com.tienda.zely.dto.person.PersonDefaultDto;
import com.tienda.zely.entity.PersonEntity;
import com.tienda.zely.entity.ProductEntity;
import com.tienda.zely.mapper.MeasurementUnityMapper;
import com.tienda.zely.mapper.PersonMapper;
import com.tienda.zely.service.MeasurementUnityService;
import com.tienda.zely.service.ParameterService;
import com.tienda.zely.service.PersonService;
import com.tienda.zely.service.ProductService;
import com.tienda.zely.service.TypeSaleService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParameterServiceImpl implements ParameterService {

    private final PersonService personService;
    private final ProductService productService;
    private final TypeSaleService typeSaleService;
    private final MeasurementUnityService measurementUnityService;
    private final PersonMapper personMapper;
    private final MeasurementUnityMapper measurementUnityMapper;

    @Override
    public ParameterSaleDto getSaleParameters() {
        log.info("Consultando parametros para registro de venta");
        return ParameterSaleDto.builder()
                .personas(personService.getAllActivePersons())
                .productos(productService.getAllActiveProducts())
                .tipoVentas(typeSaleService.getAllActiveTypeSales())
                .build();
    }

    @Override
    public ParameterProductAndUnityDto getProductAndUnityParameters(Integer productId) {
        log.info("Consultando parametros de unidad para producto ID: {}", productId);

        ProductEntity product = productService.getActiveProductOrThrow(productId);
        int idFather = product.getTypeProductEntity().getIdTypeProduct() == 2 ? 2 : 1;

        return ParameterProductAndUnityDto.builder()
                .datosUnidad(measurementUnityMapper.mapListMeasurementUnityEntityToMeasurementUnityResponseById(
                        measurementUnityService.getListMeasurementUnityEntityByIdFather(idFather)))
                .precio(product.getPrice())
                .build();
    }

    @Override
    public Optional<List<PersonDefaultDto>> getPersonsWithUnpaidSales() {
        log.info("Consultando personas con ventas sin pagar");
        List<PersonEntity> persons = personService.getAllPersonEntityWhenHasSaleWithoutPaid();
        if (persons.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(personMapper.mapListPersonEntityToPersonDefaultDto(persons));
    }
}
