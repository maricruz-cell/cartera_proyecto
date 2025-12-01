package com.cartera.repository;

import com.cartera.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    // ✅ Buscar rol por descripción, sin importar mayúsculas/minúsculas
    Optional<Rol> findByDesRolIgnoreCase(String desRol);
}
