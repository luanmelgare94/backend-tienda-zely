package com.tienda.zely.repository;

import com.tienda.zely.entity.PersonEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PersonRepository extends JpaRepository<PersonEntity, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.persona SET tiene_cuenta = ?1, limite_cuenta = ?2 WHERE id_persona = ?3")
    public int updateHasAccountPersonEntityActiveByIdPerson(boolean account, double limitAccount, Integer id);

    public boolean existsPersonEntityByFullName(String fullName);

    public PersonEntity getByFullName(String fullName);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.persona SET activo = ?1 WHERE id_persona = ?2")
    public int updateActiveOfPersonEntityById(boolean active, Integer id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE sch_zely.persona SET nombre_completo = ?1, observacion = ?2, " +
            "tiene_cuenta = ?3, limite_cuenta = ?4 WHERE id_persona = ?5")
    public int updatePersonEntityFullNameAndObservationAndHasAccountAndAccountLimitByIdPerson(String fullName,
                                                                                              String observation,
                                                                                              boolean hasAccount,
                                                                                              double accountLimit,
                                                                                              Integer idPerson);

    @Query(nativeQuery = true,
            value = "SELECT p.* FROM sch_zely.persona p INNER JOIN sch_zely.venta v ON p.id_persona = v.id_persona " +
                    "WHERE v.pagado = false AND v.activo = true AND p.activo = true GROUP BY p.id_persona")
    public List<PersonEntity> getAllPersonEntityWhenHasSaleWithoutPaid();

}