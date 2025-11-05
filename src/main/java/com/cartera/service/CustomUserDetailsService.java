package com.cartera.service;

import com.cartera.model.Persona;
import com.cartera.model.UsuarioRol;
import com.cartera.repository.PersonaRepository;
import com.cartera.repository.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String curp) throws UsernameNotFoundException {

        Persona persona = personaRepository.findByCurp(curp)
                .orElseThrow(() -> new UsernameNotFoundException("CURP no encontrada: " + curp));

        List<UsuarioRol> roles = usuarioRolRepository.findByPersona_IdPersona(persona.getIdPersona());

        if (roles.isEmpty()) {
            throw new UsernameNotFoundException("El usuario no tiene roles asignados");
        }

        List<GrantedAuthority> authorities = roles.stream()
                .map(ur -> new SimpleGrantedAuthority(ur.getRol().getDesRol())) // SIN "ROLE_"
                .collect(Collectors.toList());

        return new User(
                persona.getCurp(),
                persona.getContrasenaBase64(),
                persona.getActivo(),
                true, true, true,
                authorities
        );
    }
}
