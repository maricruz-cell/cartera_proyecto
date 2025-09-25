package com.cartera.auth.service;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String curp) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCurp(curp)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + curp));

        // Determinar rol
        String rol = "USER"; // valor por defecto
        if (usuario.getCveUsergroup() != null) {
            if (usuario.getCveUsergroup() == 1) {
                rol = "ADMIN";
            } else if (usuario.getCveUsergroup() == 2) {
                rol = "ASPIRANTE";
            }
        }

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + rol);

        return User.builder()
                .username(usuario.getCurp())          //  usamos la CURP como username
                .password(usuario.getPassword())      // contraseña encriptada
                .authorities(Collections.singletonList(authority))
                .build();
    }
}
