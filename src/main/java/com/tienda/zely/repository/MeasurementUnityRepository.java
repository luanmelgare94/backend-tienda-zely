package com.tienda.zely.repository;

import com.tienda.zely.entity.MeasurementUnityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementUnityRepository extends JpaRepository<MeasurementUnityEntity, Integer> {

    public MeasurementUnityEntity getByIdFatherAndValue(Integer IdFather, String value);

}