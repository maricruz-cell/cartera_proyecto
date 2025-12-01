package com.cartera.security;



import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import com.cartera.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonaRepository personaRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String curp) throws UsernameNotFoundException {
        Persona persona = personaRepository.findByCurp(curp.toUpperCase())
                .orElseThrow(() -> new UsernameNotFoundException("CURP no encontrado"));

        List<String> roles = usuarioRolRepository.findRolesByPersonaId(persona.getId_persona());
        List<GrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                persona.getCurp(),
                persona.getContrasena_base64(),
                persona.getActivo(),
                true, true, true,
                authorities
        );
    }
}
