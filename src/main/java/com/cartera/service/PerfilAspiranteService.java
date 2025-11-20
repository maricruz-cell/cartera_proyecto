package com.cartera.service;

import com.cartera.dto.PerfilAspiranteDTO;
import com.cartera.model.Persona;
import com.cartera.model.FotoUsuario;
import com.cartera.repository.PersonaRepository;
import com.cartera.repository.FotoUsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerfilAspiranteService {

    private final PersonaRepository personaRepository;
    private final FotoUsuarioRepository fotoUsuarioRepository;

    public PerfilAspiranteDTO obtenerPerfil(String username) {

        // Buscar persona por su usuario de login
        Persona persona = personaRepository.findByNomUsuario(username)
                .orElse(null);

        if (persona == null) {
            return null;
        }

        // Buscar foto
        FotoUsuario foto = fotoUsuarioRepository.findByIdPersona(persona.getId_persona());

        PerfilAspiranteDTO dto = new PerfilAspiranteDTO();
        dto.setNombreCompleto(
                persona.getNombre() + " " +
                        persona.getApellido_paterno() + " " +
                        persona.getApellido_materno()
        );

        dto.setRutaFoto(foto != null ? foto.getRutaFoto()
                : "/img/icon-user.png");

        return dto;
    }
}
