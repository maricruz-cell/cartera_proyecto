package com.cartera.repository;

import com.cartera.model.DtEscolaridad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;
@Repository


public interface DtEscolaridadRepository extends JpaRepository<DtEscolaridad, Integer> {

    List<DtEscolaridad> findByIdPersona(Long idPersona);
}
