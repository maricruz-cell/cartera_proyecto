package com.cartera.repository;

import com.cartera.model.DtCompetencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DtCompetenciasRepository extends JpaRepository<DtCompetencias, Long> {

    List<DtCompetencias> findByIdPersona(Long idPersona);

    @Transactional
    void deleteByIdPersona(Long idPersona);
}

