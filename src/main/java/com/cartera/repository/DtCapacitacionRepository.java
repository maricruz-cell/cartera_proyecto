package com.cartera.repository;

import com.cartera.model.DtCapacitacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DtCapacitacionRepository extends JpaRepository<DtCapacitacion, Integer> {

    List<DtCapacitacion> findByIdPersona(Long idPersona);
}
