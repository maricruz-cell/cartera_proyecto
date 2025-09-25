package com.cartera.auth.repository;

import com.cartera.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por CURP (que será el username de login)
    Optional<Usuario> findByCurp(String curp);
}
