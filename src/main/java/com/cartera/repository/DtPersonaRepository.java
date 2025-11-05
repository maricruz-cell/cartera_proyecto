package com.cartera.repository;

import com.cartera.model.DtPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DtPersonaRepository extends JpaRepository<DtPersona, Long> {
    Optional<DtPersona> findByIdPersona(Long idPersona);
}
