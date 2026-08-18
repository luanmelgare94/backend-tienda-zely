package com.tienda.zely.repository;

import com.tienda.zely.entity.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>, JpaSpecificationExecutor<ProductEntity> {

    @Query(value = "SELECT * FROM sch_zely.producto p WHERE p.activo = ?1 ORDER BY p.nombre ASC", nativeQuery = true)
    public List<ProductEntity> findAllByActive(boolean active);

    public boolean existsProductEntityByName(String name);

    public ProductEntity getByName(String name);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.producto SET activo = false WHERE id_Producto = ?1")
    public void updateDesactivateProductEntityActiveById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.producto SET activo = true WHERE id_Producto = ?1")
    public void updateActivateProductEntityActiveById(Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.producto SET nombre = ?1, precio = ?2, id_tipo_producto = ?3, " +
            "fecha_actualizacion = ?4 WHERE id_Producto = ?5")
    public void updateProductEntityByIdProduct(String name, double price, Integer idTipoProducto,
                                               LocalDateTime fechaActualizacion, Integer idProduct);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.producto SET precio = ?1, id_tipo_producto = ?2, " +
            "fecha_actualizacion = ?3, nombre = ?4 WHERE id_Producto = ?5")
    public void updateProductEntityByName(double price, Integer idTipoProducto, LocalDateTime fechaActualizacion,
                                          String name, Integer idProduct);

}