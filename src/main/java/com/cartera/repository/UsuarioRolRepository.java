package com.cartera.repository;

import com.cartera.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    //  Busca todos los roles asignados a una persona por su id_persona
    List<UsuarioRol> findByPersona_IdPersona(Long idPersona);
}
