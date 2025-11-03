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

        //  Buscar persona por CURP
        Persona persona = personaRepository.findByCurp(curp)
                .orElseThrow(() -> new UsernameNotFoundException("CURP no encontrada: " + curp));

        //  Obtener roles asociados a la persona
        List<UsuarioRol> usuarioRoles = usuarioRolRepository.findByPersona_IdPersona(persona.getIdPersona());

        //  Convertir roles en autoridades de Spring Security
        List<GrantedAuthority> authorities = usuarioRoles.stream()
                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRol().getDesRol().toUpperCase()))
                .collect(Collectors.toList());

        //  Crear el usuario de Spring
        return new User(
                persona.getCurp(),                // username
                persona.getContrasenaBase64(),    // password
                persona.getActivo(),              // habilitado
                true,                             // cuenta no expirada
                true,                             // credenciales no expiradas
                true,                             // cuenta no bloqueada
                authorities                       // roles
        );
    }
}
