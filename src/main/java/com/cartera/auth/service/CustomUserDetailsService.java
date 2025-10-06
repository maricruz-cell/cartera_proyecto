// src/main/java/com/cartera/auth/service/CustomUserDetailsService.java
package com.cartera.auth.service;

import com.cartera.auth.model.Rol;
import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository repo) {
        this.usuarioRepository = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No existe usuario: " + username));

        if (Boolean.FALSE.equals(u.getActivo())) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        var authorities = u.getRoles().stream()
                .map(Rol::getNombre)               // p.ej. ADMINISTRADOR
                .map(r -> "ROLE_" + r)             // ROLE_ADMINISTRADOR
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return User.withUsername(u.getUsername())
                .password(u.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}
