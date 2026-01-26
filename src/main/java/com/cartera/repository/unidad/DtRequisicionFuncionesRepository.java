package com.cartera.repository.unidad;

import com.cartera.model.DtRequisicionFunciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface DtRequisicionFuncionesRepository
        extends JpaRepository<DtRequisicionFunciones, Long> {

    // 🔹 Obtener funciones por requisición
    @Query("select f.idFuncion from DtRequisicionFunciones f where f.idRequisicion = :id")
    List<Long> findFuncionesByRequisicion(@Param("id") Long id);

    // 🔹 Eliminar funciones de la requisición
    @Modifying
    @Query(value = "delete from dt_requisicion_funciones where id_requisicion = :id",
            nativeQuery = true)
    void deleteByIdRequisicion(@Param("id") Long id);

    // 🔹 Insertar función
    @Modifying
    @Query(value = """
        insert into dt_requisicion_funciones (id_requisicion, id_funcion)
        values (:r, :f)
        """, nativeQuery = true)
    void insert(@Param("r") Long requisicion,
                @Param("f") Long funcion);
}
