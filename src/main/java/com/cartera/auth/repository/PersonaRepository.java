package com.cartera.auth.repository;

import com.cartera.auth.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByCurp(String curp);

    // ✅ Nuevo método para contar registros del año actual
    long countByFolioStartingWith(String prefix);
}
