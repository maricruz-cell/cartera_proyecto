package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class UsuarioController {

    private final PersonaRepository personaRepository;

    // ===================== MARCAR QUE YA VIO EL TOUR =====================
    @PostMapping("/usuario/marcar-tour-visto")
    @ResponseBody
    public void marcarTourVisto(Authentication auth) {

        if (auth == null || auth.getName() == null) {
            return; // no rompe nada si llega vacío
        }

        String curp = auth.getName();

        Persona persona = personaRepository.findByCurp(curp).orElse(null);

        if (persona == null) {
            return;
        }

        // Cambiar estatus
        persona.setPrimerLogin(false);

        // Guardar en BD
        personaRepository.save(persona);
    }
}
