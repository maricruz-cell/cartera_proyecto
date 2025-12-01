package com.cartera.repository;

import com.cartera.model.CatCompetencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CatCompetenciasRepository extends JpaRepository<CatCompetencias, Long> {

    List<CatCompetencias> findByTipo(String tipo);
}
