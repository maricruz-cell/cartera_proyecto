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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCurp(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Rol por defecto mientras no haya BD
        String rol = "ROLE_ASPIRANTE";
        if (usuario.getCveUsergroup() != null && usuario.getCveUsergroup() == 1) {
            rol = "ROLE_ADMIN";
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(rol);

        return new User(
                usuario.getCurp(),
                usuario.getPassword(),
                Collections.singletonList(authority)
        );
    }
}
