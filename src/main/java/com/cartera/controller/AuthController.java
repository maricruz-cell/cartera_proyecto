package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import jakarta.servlet.http.HttpSession;
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
        return "login"; // Muestra tu vista login.html
    }

    @GetMapping("/menu")
    public String menu(Authentication authentication, HttpSession session) {
        if (authentication == null) {
            return "redirect:/login";
        }

        // CURP del usuario autenticado
        String curp = authentication.getName();
        Optional<Persona> personaOpt = personaRepository.findByCurp(curp);

        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();

            // 🔹 Guardar datos del usuario en la sesión
            session.setAttribute("curp", persona.getCurp());
            session.setAttribute("idPersona", persona.getIdPersona());
            session.setAttribute("nombreCompleto",
                    persona.getNombre() + " " +
                            (persona.getApellidoPaterno() != null ? persona.getApellidoPaterno() : "") + " " +
                            (persona.getApellidoMaterno() != null ? persona.getApellidoMaterno() : ""));

            // 🔹 Obtener el rol del usuario (desde Spring Security)
            String rol = authentication.getAuthorities().iterator().next().getAuthority();

            // 🔹 Redirigir según el rol obtenido
            switch (rol) {
                case "Administrador":
                    return "redirect:/admin/menu";
                case "Reclutamiento":
                    return "redirect:/reclutamiento/menu";
                case "Unidad":
                    return "redirect:/unidad/menu";
                case "Aspirante":
                    return "redirect:/aspirante/menu";
                default:
                    return "redirect:/login?error=true";
            }
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
