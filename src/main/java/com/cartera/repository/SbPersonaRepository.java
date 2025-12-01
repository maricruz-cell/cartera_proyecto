package com.cartera.repository;

import com.cartera.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SbPersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByCorreo(String correo);
}
