package com.cartera.repository;

import com.cartera.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByCurp(String curp);

    Optional<Persona> findByCorreo(String correo);

    Optional<Persona> findByTokenValidacion(String token);

    @Query("SELECT MAX(p.idPersona) FROM Persona p")
    Long obtenerUltimoId();
}
