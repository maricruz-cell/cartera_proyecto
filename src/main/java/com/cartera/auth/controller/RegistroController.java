package com.cartera.auth.controller;

import com.cartera.auth.model.Persona;
import com.cartera.auth.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.Year;

@Controller
public class RegistroController {

    @Autowired
    private PersonaRepository personaRepository;

    // form registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("persona", new Persona());
        return "registro"; // templates/registro.html
    }

    // pocesar registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Persona persona, Model model) {

        // Validar usuario con la misma CURP
        if (personaRepository.findByCurp(persona.getCurp()).isPresent()) {
            model.addAttribute("error", "Ya existe una cuenta registrada con esa CURP.");
            return "registro";
        }

        // Generar folio automático con prefijo + año actual + consecutivo
        String añoActual = String.valueOf(Year.now().getValue());
        long total = personaRepository.count() + 1; // siguiente consecutivo
        String folio = String.format("ASP%s%06d", añoActual, total);
        persona.setFolio(folio);

        // Activar el usuario por defecto
        persona.setActivo(true);

        // Guardar la persona en la base de datos
        personaRepository.save(persona);

        // Mostrar confirmación
        model.addAttribute("folio", folio);
        model.addAttribute("curp", persona.getCurp());
        return "registro_exitoso"; // templates/registro_exitoso.html
    }
}
