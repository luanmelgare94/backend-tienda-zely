package com.tienda.zely.service;

import com.tienda.zely.dto.serialnumber.SerialNumberRequestDto;
import com.tienda.zely.dto.serialnumber.SerialNumberResponseDto;
import java.util.List;

public interface SerialNumberService {

    SerialNumberResponseDto registerSerialNumber(SerialNumberRequestDto serialNumberRequestDto);

    List<SerialNumberResponseDto> findAllByIdProduct(Integer idProduct);

    SerialNumberResponseDto findBySerialNumber(String serialNumber);
}
