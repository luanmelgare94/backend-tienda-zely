package com.tienda.zely.service.impl;

import com.tienda.zely.entity.MeasurementUnityEntity;
import com.tienda.zely.repository.MeasurementUnityRepository;
import com.tienda.zely.service.MeasurementUnityService;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MeasurementUnityServiceImpl implements MeasurementUnityService {

    @Autowired
    private MeasurementUnityRepository measurementUnityRepository;

    @Override
    public List<MeasurementUnityEntity> getAllMeasurementUnityEntityByActivated() {
        log.info("MeasurementUnityServiceImpl.getAllMeasurementUnityEntityByActivated");
        return measurementUnityRepository.findAll().stream()
                .filter(MeasurementUnityEntity::isActive)
                .sorted(Comparator.comparing(MeasurementUnityEntity::getUnity))
                .collect(Collectors.toList());
    }

    @Override
    public List<MeasurementUnityEntity> getListMeasurementUnityEntityByIdFather(Integer idFather) {
        log.info("MeasurementUnityServiceImpl.getListMeasurementUnityEntityByIdFather");
        log.info("MeasurementUnityServiceImpl.getListMeasurementUnityEntityByIdFather.idFather: " + idFather);
        //List<MeasurementUnityEntity> measurementUnityEntities = measurementUnityRepository.findAll();
        return measurementUnityRepository.findAll().stream()
                .filter(measurementUnityEntity -> measurementUnityEntity.getIdFather() != null)
                .filter(measurementUnityEntity -> measurementUnityEntity.getIdFather().equals(idFather))
                .collect(Collectors.toList());
    }

    @Override
    public MeasurementUnityEntity getMeasurementUnityEntityByIdFatherAndValue(Integer idFather, String value) {
        log.info("MeasurementUnityServiceImpl.getMeasurementUnityEntityByIdFatherAndValue");
        log.info("MeasurementUnityServiceImpl.getMeasurementUnityEntityByIdFatherAndValue.idFather" + idFather);
        log.info("MeasurementUnityServiceImpl.getMeasurementUnityEntityByIdFatherAndValue.value" + value);
        return measurementUnityRepository.getByIdFatherAndValue(idFather, value);
    }

}