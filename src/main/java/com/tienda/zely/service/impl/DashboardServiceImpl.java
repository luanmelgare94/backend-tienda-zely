package com.tienda.zely.service.impl;

import com.tienda.zely.dto.dashboard.DashboardDefaultDto;
import com.tienda.zely.service.DashboardService;
import com.tienda.zely.service.PersonService;
import com.tienda.zely.service.ProductService;
import com.tienda.zely.service.SaleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final PersonService personService;
    private final ProductService productService;
    private final SaleService saleService;

    @Override
    public DashboardDefaultDto getStatistics() {
        log.info("Consultando estadisticas del dashboard");
        return DashboardDefaultDto.builder()
                .cantidadPersonas(personService.getAllActivePersons().size())
                .cantidadProductos(productService.getAllActiveProducts().size())
                .cantidadVentasEnDia(saleService.getQuantityOfSaleEntityByDateOfTheDay())
                .cantidadVentasPorPagar(saleService.getAllSaleEntityNotPaidAndActivated().size())
                .build();
    }
}
