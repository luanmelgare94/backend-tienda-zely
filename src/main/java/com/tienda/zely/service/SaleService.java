package com.tienda.zely.service;

import com.tienda.zely.dto.sale.SaleDefaultDto;
import com.tienda.zely.dto.sale.SaleRequestDto;
import com.tienda.zely.dto.sale.SaleResponseByIdDto;
import com.tienda.zely.entity.SaleEntity;
import java.util.List;
import java.util.Optional;

public interface SaleService {

    void registerSale(SaleRequestDto saleRequestDto);

    List<SaleDefaultDto> getAllActiveSales();

    Optional<SaleResponseByIdDto> findSaleById(Integer id);

    List<SaleDefaultDto> getAllPaidAndActivatedSales();

    List<SaleDefaultDto> getPaidAndActivatedSalesByPerson(Integer idPerson);

    List<SaleDefaultDto> getAllNotPaidAndActivatedSales();

    List<SaleDefaultDto> getNotPaidAndActivatedSalesByPerson(Integer idPerson);

    boolean markSaleAsPaid(Integer id);

    SaleEntity registerSaleEntity(SaleEntity saleEntity);

    List<SaleEntity> getAllSaleEntityActivated();

    Long getQuantityOfSaleEntityByDateOfTheDay();

    List<SaleEntity> getAllSaleEntityPaidAndActivated();

    List<SaleEntity> getAllSaleEntityPaidAndActivatedByIdPerson(Integer id);

    List<SaleEntity> getAllSaleEntityNotPaidAndActivated();

    List<SaleEntity> getAllSaleEntityNotPaidAndActivatedByIdPerson(Integer id);

    boolean paidSaleEntityByIdSale(Integer id);

    SaleEntity getSaleEntityById(Integer id);
}
