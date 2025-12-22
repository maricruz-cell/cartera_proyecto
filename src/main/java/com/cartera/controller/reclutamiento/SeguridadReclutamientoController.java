package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reclutamiento")
@RequiredArgsConstructor
public class SeguridadController {

    private final PersonaRepository personaRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 👉 CARGA LA PANTALLA
    @GetMapping("/cambiar-password")
    public String cambiarPassword(Model model, Authentication auth) {

        String correo = auth.getName();
        Persona persona = personaRepository.findByCorreo(correo)
                .orElseThrow();

        model.addAttribute("persona", persona);
        return "reclutamiento/cambiar-password";
    }

    // 👉 GUARDA LA NUEVA CONTRASEÑA
    @PostMapping("/cambiar-password")
    public String actualizarPassword(
            @RequestParam("nuevaPassword") String nuevaPassword,
            Authentication auth,
            RedirectAttributes ra
    ) {

        String correo = auth.getName();
        Persona persona = personaRepository.findByCorreo(correo)
                .orElseThrow();

        persona.setContrasena_base64(
                passwordEncoder.encode(nuevaPassword)
        );
        persona.setPrimerLogin(false);

        personaRepository.save(persona);

        ra.addFlashAttribute("ok", "Contraseña actualizada correctamente");
        return "redirect:/reclutamiento/cambiar-password";
    }
}
