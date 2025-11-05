package com.cartera.service;

import com.cartera.model.DtPersona;
import com.cartera.model.Persona;
import com.cartera.repository.DtPersonaRepository;
import com.cartera.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AspiranteService {

    private final PersonaRepository personaRepository;     // sb_persona
    private final DtPersonaRepository dtPersonaRepository; // dt_persona

    // 🔹 Datos de sb_persona
    public Persona obtenerDatosBasicos(String curp) {
        return personaRepository.findByCurp(curp).orElse(null);
    }

    // 🔹 Datos de dt_persona
    public DtPersona obtenerDatosDetallados(Long idPersona) {
        return dtPersonaRepository.findByIdPersona(idPersona).orElse(null);
    }

    // 🔹 Guardar o actualizar dt_persona
    public void guardarOActualizar(DtPersona persona) {
        dtPersonaRepository.save(persona);
    }
}
