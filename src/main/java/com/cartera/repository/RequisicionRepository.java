package com.cartera.repository;

import com.cartera.model.DtRequisicion;
import com.cartera.projection.VacanteProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequisicionRepository extends CrudRepository<DtRequisicion, Long> {

    @Query(value = """
            SELECT
                r.id_requisicion AS idRequisicion,
                r.folio AS folio,
                r.fecha_publicacion AS fechaRegistro,
                ep.des_estatus AS estatus,
                r.titulo AS puesto,
                r.num_vacantes AS numVacantes,
                tc.des_tipo AS tipoContratacion
            FROM dt_requisicion r
            LEFT JOIN cat_estatus_plaza ep ON ep.id_estatus_plaza = r.id_estatus_plaza
            LEFT JOIN cat_tipo_contratacion tc ON tc.id_tipo_contratacion = r.id_tipo_contratacion
            ORDER BY r.id_requisicion ASC
            """, nativeQuery = true)
    List<VacanteProjection> obtenerVacantes();
}
