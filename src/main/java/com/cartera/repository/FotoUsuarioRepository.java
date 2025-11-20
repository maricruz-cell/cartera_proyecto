package com.cartera.repository;

import com.cartera.model.FotoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoUsuarioRepository extends JpaRepository<FotoUsuario, Long> {

    FotoUsuario findByIdPersona(Long idPersona);  // ← AHORA SÍ EXISTE 🔥
}
