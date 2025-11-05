package com.cartera.auth.controller;

import com.cartera.auth.model.VerificacionToken;
import com.cartera.auth.repository.VerificacionTokenRepository;
import com.cartera.auth.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.time.LocalDateTime;

@Controller
public class VerificacionController {

    @Autowired
    private VerificacionTokenRepository tokenRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping("/verificar")
    public String verificarCuenta(@RequestParam("token") String token, Model model) {
        VerificacionToken verToken = tokenRepository.findByToken(token).orElse(null);

        if (verToken == null) {
            model.addAttribute("error", "Token inválido.");
            return "verificacion_error";
        }

        if (verToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "El enlace ha expirado.");
            return "verificacion_error";
        }

        var persona = verToken.getPersona();
        persona.setActivo(true);
        personaRepository.save(persona);

        model.addAttribute("mensaje", "Tu cuenta ha sido verificada con éxito. Ya puedes iniciar sesión.");
        return "verificacion_exitosa";
    }
}
