package com.tienda.zely.service.impl;

import static com.tienda.zely.util.Constants.TIME_ZONE;

import com.tienda.zely.dto.serialnumber.SerialNumberRequestDto;
import com.tienda.zely.dto.serialnumber.SerialNumberResponseDto;
import com.tienda.zely.entity.ProductEntity;
import com.tienda.zely.entity.SerialNumberEntity;
import com.tienda.zely.exception.ConflictException;
import com.tienda.zely.exception.ResourceNotFoundException;
import com.tienda.zely.mapper.SerialNumberMapper;
import com.tienda.zely.repository.SerialNumberRepository;
import com.tienda.zely.service.ProductService;
import com.tienda.zely.service.SerialNumberService;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SerialNumberServiceImpl implements SerialNumberService {

    private final SerialNumberRepository serialNumberRepository;
    private final ProductService productService;
    private final SerialNumberMapper serialNumberMapper;

    @Override
    @Transactional
    public SerialNumberResponseDto registerSerialNumber(SerialNumberRequestDto serialNumberRequestDto) {
        log.info("Registrando numero de serie para producto ID: {}", serialNumberRequestDto.getCodigoProducto());

        String normalizedSerialNumber = normalizeSerialNumber(serialNumberRequestDto.getNumeroSerie());

        if (serialNumberRepository.existsBySerialNumber(normalizedSerialNumber)) {
            throw new ConflictException("El numero de serie ingresado ya existe. Favor ingresar otro.");
        }

        ProductEntity product = productService.getActiveProductOrThrow(serialNumberRequestDto.getCodigoProducto());

        LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
        SerialNumberRequestDto normalizedRequest = new SerialNumberRequestDto(
                serialNumberRequestDto.getCodigoProducto(),
                serialNumberRequestDto.isTieneCodigoPropio(),
                normalizedSerialNumber);

        SerialNumberEntity entity = serialNumberMapper.mapToSerialNumberEntity(normalizedRequest, now, product);
        SerialNumberEntity savedEntity = serialNumberRepository.save(entity);

        return serialNumberMapper.mapExceptPriceResponseDto(savedEntity);
    }

    @Override
    public List<SerialNumberResponseDto> findAllByIdProduct(Integer idProduct) {
        log.info("Consultando numeros de serie para producto ID: {}", idProduct);

        productService.getActiveProductOrThrow(idProduct);

        List<SerialNumberEntity> serialNumberEntities = serialNumberRepository.findByProductEntityIdProduct(idProduct);

        return serialNumberEntities.stream()
                .map(serialNumberMapper::mapOnlySerialNumberToResponseDto)
                .toList();
    }

    @Override
    public SerialNumberResponseDto findBySerialNumber(String serialNumber) {
        log.info("Buscando numero de serie: {}", serialNumber);

        String normalizedSerialNumber = normalizeSerialNumber(serialNumber);

        SerialNumberEntity serialNumberEntity = serialNumberRepository
                .findBySerialNumberAndProductActive(normalizedSerialNumber, true)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "El numero de serie consultado no corresponde a ningun producto. Contacte con el administrador"));

        return serialNumberMapper.mapToSerialNumberResponseDto(serialNumberEntity);
    }

    private String normalizeSerialNumber(String serialNumber) {
        return serialNumber.trim().toUpperCase();
    }
}
