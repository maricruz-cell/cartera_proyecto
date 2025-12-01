package com.cartera.repository;

import com.cartera.model.VwCapacitacionCompleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VwCapacitacionCompletaRepository
        extends JpaRepository<VwCapacitacionCompleta, Integer> {

    List<VwCapacitacionCompleta> findByIdPersona(Long idPersona);
}
