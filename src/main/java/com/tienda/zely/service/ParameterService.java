package com.tienda.zely.service;

import com.tienda.zely.dto.parameter.ParameterProductAndUnityDto;
import com.tienda.zely.dto.parameter.ParameterSaleDto;
import com.tienda.zely.dto.person.PersonDefaultDto;
import java.util.List;
import java.util.Optional;

public interface ParameterService {

    ParameterSaleDto getSaleParameters();

    ParameterProductAndUnityDto getProductAndUnityParameters(Integer productId);

    Optional<List<PersonDefaultDto>> getPersonsWithUnpaidSales();
}
