package com.cartera.repository;

import com.cartera.model.DtExperienciaLaboral;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface DtExperienciaLaboralRepository
        extends JpaRepository<DtExperienciaLaboral, Integer> {

    List<DtExperienciaLaboral> findByIdPersona(Long idPersona);
}
