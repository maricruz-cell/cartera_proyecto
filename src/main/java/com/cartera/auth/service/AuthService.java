package com.cartera.auth.service;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void actualizarPassword(String username, String nuevaPassword) {
        Usuario u = findByUsername(username);
        u.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuarioRepository.save(u);
    }
}
