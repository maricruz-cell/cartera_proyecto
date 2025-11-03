package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/menu")
    public String menu(Authentication authentication) {
        if (authentication == null) {
            return "redirect:/login";
        }

        String curp = authentication.getName();
        Optional<Persona> personaOpt = personaRepository.findByCurp(curp);

        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();


            String rol = persona.getCurp().toUpperCase();

            // Según la  CURP se redirije a su rol y menu
            if (rol.contains("ADMIN")) {
                return "menu_admin";
            } else if (rol.contains("RECLU")) {
                return "menu_reclutamiento";
            } else if (rol.contains("UNID")) {
                return "menu_unidad";
            } else {
                return "menu_aspirante";
            }
        }

        return "redirect:/login";
    }
}
