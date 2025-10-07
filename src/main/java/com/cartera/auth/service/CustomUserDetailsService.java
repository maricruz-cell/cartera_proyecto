package com.cartera.auth.service;

import com.cartera.auth.model.Persona;
import com.cartera.auth.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public UserDetails loadUserByUsername(String curp) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByCurp(curp)
                .orElseThrow(() -> new UsernameNotFoundException("CURP no encontrado"));

        if (Boolean.FALSE.equals(persona.getActivo())) {
            throw new UsernameNotFoundException("Usuario inactivo");
        }

        var authorities = persona.getRoles().stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getDesRol().toUpperCase()))
                .collect(Collectors.toSet());

        return User.builder()
                .username(persona.getCurp())
                .password(persona.getPasswordHash())
                .authorities(authorities)
                .build();
    }
}
