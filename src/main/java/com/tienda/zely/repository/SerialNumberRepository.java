package com.tienda.zely.repository;

import com.tienda.zely.entity.SerialNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SerialNumberRepository extends JpaRepository<SerialNumberEntity, Long> {

    boolean existsBySerialNumber(String serialNumber);

    List<SerialNumberEntity> findByProductEntityIdProduct(Integer idProduct);

    @Query("SELECT s FROM SerialNumberEntity s JOIN FETCH s.productEntity p "
            + "WHERE s.serialNumber = :serialNumber AND p.active = :active")
    Optional<SerialNumberEntity> findBySerialNumberAndProductActive(
            @Param("serialNumber") String serialNumber,
            @Param("active") boolean active);
}
