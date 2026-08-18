package com.tienda.zely.service.impl;

import com.tienda.zely.dto.detailsale.DetailSaleSpecial;
import com.tienda.zely.entity.DetailSaleEntity;
import com.tienda.zely.entity.SaleEntity;
import com.tienda.zely.mapper.DetailSaleMapper;
import com.tienda.zely.repository.SaleRepository;
import com.tienda.zely.service.DetailSaleService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailSaleServiceImpl implements DetailSaleService {

    private final SaleRepository saleRepository;
    private final DetailSaleMapper detailSaleMapper;

    @Override
    public Optional<DetailSaleSpecial> getDetailSalesToPayByPerson(Integer idPerson) {
        log.info("Consultando detalles de venta por pagar para persona ID: {}", idPerson);

        List<DetailSaleEntity> detailSaleEntities = findUnpaidDetailSalesByPerson(idPerson);
        if (detailSaleEntities.isEmpty()) {
            return Optional.empty();
        }

        DetailSaleEntity firstDetail = detailSaleEntities.get(0);
        return Optional.of(DetailSaleSpecial.builder()
                .codigoCliente(firstDetail.getSaleEntity().getPersonEntity().getIdPerson())
                .nombreCliente(firstDetail.getSaleEntity().getPersonEntity().getFullName())
                .detallesVenta(detailSaleMapper.mapListDetailSaleEntityToDetalleVenta(detailSaleEntities))
                .precioFinal(detailSaleEntities.stream().mapToDouble(DetailSaleEntity::getPrice).sum())
                .build());
    }

    private List<DetailSaleEntity> findUnpaidDetailSalesByPerson(Integer idPerson) {
        List<SaleEntity> unpaidSales = saleRepository.getAllEntityByActiveAndPaidAndIdPerson(
                Boolean.TRUE, Boolean.FALSE, idPerson);

        List<DetailSaleEntity> detailSaleEntities = new ArrayList<>();
        unpaidSales.forEach(sale -> detailSaleEntities.addAll(sale.getDetailSaleEntityList()));
        return detailSaleEntities;
    }
}
