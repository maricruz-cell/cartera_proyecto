package com.cartera.auth.repository;

import com.cartera.auth.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FakeUsuarioRepository {

    private final Map<String, Usuario> usuarios = new HashMap<>();

    public Optional<Usuario> findByCurp(String curp) {
        return Optional.ofNullable(usuarios.get(curp));
    }

    public Usuario save(Usuario usuario) {
        usuarios.put(usuario.getCurp(), usuario);
        return usuario;
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios.values());
    }
}
