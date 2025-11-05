package com.cartera.repository;

import com.cartera.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    List<UsuarioRol> findByPersona_IdPersona(Long idPersona);
}
