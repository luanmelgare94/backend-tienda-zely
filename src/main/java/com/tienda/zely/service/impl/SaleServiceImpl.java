package com.tienda.zely.service.impl;

import static com.tienda.zely.util.Constants.TIME_ZONE;

import com.tienda.zely.dto.sale.SaleDefaultDto;
import com.tienda.zely.dto.sale.SaleRequestDto;
import com.tienda.zely.dto.sale.SaleResponseByIdDto;
import com.tienda.zely.entity.MeasurementUnityEntity;
import com.tienda.zely.entity.SaleEntity;
import com.tienda.zely.exception.ResourceNotFoundException;
import com.tienda.zely.mapper.SaleMapper;
import com.tienda.zely.repository.PersonRepository;
import com.tienda.zely.repository.SaleRepository;
import com.tienda.zely.service.MeasurementUnityService;
import com.tienda.zely.service.SaleService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final PersonRepository personRepository;
    private final MeasurementUnityService measurementUnityService;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public void registerSale(SaleRequestDto saleRequestDto) {
        log.info("Registrando venta para persona ID: {}", saleRequestDto.getPersona().getCodigo());

        List<MeasurementUnityEntity> measurementUnityEntities = resolveMeasurementUnities(saleRequestDto);
        SaleEntity saleEntity = saleMapper.mapSaleRequestDtoToSaleEntity(saleRequestDto, measurementUnityEntities);
        registerSaleEntity(saleEntity);
    }

    @Override
    public List<SaleDefaultDto> getAllActiveSales() {
        return saleMapper.mapListSaleEntityToSaleDefaultDto(getAllSaleEntityActivated());
    }

    @Override
    public Optional<SaleResponseByIdDto> findSaleById(Integer id) {
        return saleRepository.findById(id)
                .filter(sale -> sale.getIdSale() != null)
                .map(saleMapper::mapSaleEntityToSaleResponseByIdDto);
    }

    @Override
    public List<SaleDefaultDto> getAllPaidAndActivatedSales() {
        return saleMapper.mapListSaleEntityToSaleDefaultDto(getAllSaleEntityPaidAndActivated());
    }

    @Override
    public List<SaleDefaultDto> getPaidAndActivatedSalesByPerson(Integer idPerson) {
        return saleMapper.mapListSaleEntityToSaleDefaultDto(getAllSaleEntityPaidAndActivatedByIdPerson(idPerson));
    }

    @Override
    public List<SaleDefaultDto> getAllNotPaidAndActivatedSales() {
        return saleMapper.mapListSaleEntityToSaleDefaultDto(getAllSaleEntityNotPaidAndActivated());
    }

    @Override
    public List<SaleDefaultDto> getNotPaidAndActivatedSalesByPerson(Integer idPerson) {
        return saleMapper.mapListSaleEntityToSaleDefaultDto(getAllSaleEntityNotPaidAndActivatedByIdPerson(idPerson));
    }

    @Override
    @Transactional
    public boolean markSaleAsPaid(Integer id) {
        return paidSaleEntityByIdSale(id);
    }

    @Override
    public SaleEntity registerSaleEntity(SaleEntity saleEntity) {
        log.info("Persistiendo venta");
        if (saleEntity.isPaid()) {
            saleEntity.setDatePaid(LocalDateTime.now(ZoneId.of(TIME_ZONE)));
        }
        return saleRepository.save(saleEntity);
    }

    @Override
    public List<SaleEntity> getAllSaleEntityActivated() {
        log.info("Consultando ventas activas");
        return saleRepository.getAllEntityByActive(Boolean.TRUE);
    }

    @Override
    public Long getQuantityOfSaleEntityByDateOfTheDay() {
        log.info("Consultando cantidad de ventas del dia");
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        return saleRepository.findAll()
                .stream()
                .filter(saleEntity -> saleEntity.isActive()
                        && Objects.equals(
                                saleEntity.getDateRegister().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")),
                                localDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))))
                .count();
    }

    @Override
    public List<SaleEntity> getAllSaleEntityPaidAndActivated() {
        log.info("Consultando ventas pagadas y activas");
        return saleRepository.getAllEntityByActiveAndPaid(Boolean.TRUE, Boolean.TRUE);
    }

    @Override
    public List<SaleEntity> getAllSaleEntityPaidAndActivatedByIdPerson(Integer id) {
        log.info("Consultando ventas pagadas para persona ID: {}", id);
        if (personRepository.existsById(id)) {
            return saleRepository.getAllEntityByActiveAndPaidAndIdPerson(Boolean.TRUE, Boolean.TRUE, id);
        }
        return new ArrayList<>();
    }

    @Override
    public List<SaleEntity> getAllSaleEntityNotPaidAndActivated() {
        log.info("Consultando ventas sin pagar y activas");
        return saleRepository.getAllEntityByActiveAndPaid(Boolean.TRUE, Boolean.FALSE);
    }

    @Override
    public List<SaleEntity> getAllSaleEntityNotPaidAndActivatedByIdPerson(Integer id) {
        log.info("Consultando ventas sin pagar para persona ID: {}", id);
        if (personRepository.existsById(id)) {
            return saleRepository.getAllEntityByActiveAndPaidAndIdPerson(Boolean.TRUE, Boolean.FALSE, id);
        }
        return new ArrayList<>();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean paidSaleEntityByIdSale(Integer id) {
        log.info("Marcando venta como pagada ID: {}", id);
        if (saleRepository.existsById(id)) {
            return saleRepository.updateSaleEntityPaidAndDatePaidByIdSale(true,
                    LocalDateTime.now(ZoneId.of(TIME_ZONE)), id) == 1;
        }
        return false;
    }

    @Override
    public SaleEntity getSaleEntityById(Integer id) {
        log.info("Consultando venta por ID: {}", id);
        return saleRepository.findById(id).orElse(new SaleEntity());
    }

    private List<MeasurementUnityEntity> resolveMeasurementUnities(SaleRequestDto saleRequestDto) {
        List<MeasurementUnityEntity> measurementUnityEntities = new ArrayList<>();
        for (SaleRequestDto.DetailSaleRequestDto detail : saleRequestDto.getDetalleVenta()) {
            MeasurementUnityEntity entity = measurementUnityService.getMeasurementUnityEntityByIdFatherAndValue(
                    detail.getUnidad().getCodigoPadre(), detail.getUnidad().getCodigoValor());
            if (entity == null || entity.getIdMeasurementUnity() == null) {
                throw new ResourceNotFoundException(
                        "Unidad de medida no encontrada para codigoPadre: " + detail.getUnidad().getCodigoPadre()
                                + " y codigoValor: " + detail.getUnidad().getCodigoValor());
            }
            measurementUnityEntities.add(entity);
        }
        return measurementUnityEntities;
    }
}
