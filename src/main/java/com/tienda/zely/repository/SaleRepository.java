package com.tienda.zely.repository;

import com.tienda.zely.entity.SaleEntity;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<SaleEntity, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.venta SET pagado = ?1, fecha_pago = ?2 WHERE id_venta = ?3")
    public int updateSaleEntityPaidAndDatePaidByIdSale(boolean pagado, LocalDateTime fechaPago, Integer idVenta);

    @Query(nativeQuery = true, value = "SELECT * FROM sch_zely.venta WHERE activo = ?1 AND pagado = ?2 AND id_persona = ?3 ORDER BY id_venta ASC")
    public List<SaleEntity> getAllEntityByActiveAndPaidAndIdPerson(boolean active, boolean paid, Integer idPerson);

    @Query(nativeQuery = true, value = "SELECT * FROM sch_zely.venta WHERE activo = ?1 AND pagado = ?2 ORDER BY id_venta ASC")
    public List<SaleEntity> getAllEntityByActiveAndPaid(boolean active, boolean paid);

    @Query(nativeQuery = true, value = "SELECT * FROM sch_zely.venta WHERE activo = ?1 ORDER BY id_venta ASC")
    public List<SaleEntity> getAllEntityByActive(boolean active);

}