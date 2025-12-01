package com.cartera.repository;

import com.cartera.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    // ✅ Consulta que sí coincide con tu estructura actual (usa id_rol directamente)
    @Query(value = """
        SELECT r.des_rol
        FROM public.sb_usuario_rol ur
        INNER JOIN public.sb_rol r ON ur.id_rol = r.id_rol
        WHERE ur.id_persona = :idPersona
        """, nativeQuery = true)
    List<String> findRolesByPersonaId(Long idPersona);
}
