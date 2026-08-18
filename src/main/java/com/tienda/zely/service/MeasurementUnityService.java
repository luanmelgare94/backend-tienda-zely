package com.tienda.zely.service;

import com.tienda.zely.entity.MeasurementUnityEntity;

import java.util.List;

public interface MeasurementUnityService {

    List<MeasurementUnityEntity> getAllMeasurementUnityEntityByActivated();

    List<MeasurementUnityEntity> getListMeasurementUnityEntityByIdFather(Integer idFather);

    MeasurementUnityEntity getMeasurementUnityEntityByIdFatherAndValue(Integer idFather, String value);

}