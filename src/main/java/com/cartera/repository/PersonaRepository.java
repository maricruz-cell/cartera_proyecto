
package com.cartera.repository;

import com.cartera.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    Optional<Persona> findByCurp(String curp);
    Optional<Persona> findByCorreo(String correo);
    Optional<Persona> findByTokenValidacion(String token);

    // 🔥 CORRECCIÓN OBLIGATORIA POR EL ERROR
    @Query("SELECT p FROM Persona p WHERE p.nombre = :username")
    Optional<Persona> findByNomUsuario(@Param("username") String username);

    @Query("""
       SELECT p FROM Persona p
       WHERE p.folio LIKE CONCAT('ASP', :year, '%')
       ORDER BY p.folio DESC
    """)
    List<Persona> findTopByYearOrderByFolioDesc(@Param("year") String year);
}
