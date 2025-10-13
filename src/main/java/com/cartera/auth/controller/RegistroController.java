package com.cartera.auth.controller;

import com.cartera.auth.model.Persona;
import com.cartera.auth.model.Rol;
import com.cartera.auth.repository.PersonaRepository;
import com.cartera.auth.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistroController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RolRepository rolRepository;

    // Mostrar el formulario de registro
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("persona", new Persona());
        return "registro"; // templates/registro.html
    }

    // Procesar el formulario de registro
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Persona persona, Model model) {

        // 1️⃣ Validar si la CURP ya existe
        if (personaRepository.findByCurp(persona.getCurp()).isPresent()) {
            model.addAttribute("error", "Ya existe una cuenta registrada con esa CURP.");
            return "registro";
        }

        // 2️⃣ Generar folio automático (ASP + año + consecutivo)
        String añoActual = String.valueOf(Year.now().getValue());
        long consecutivo = personaRepository.countByFolioStartingWith("ASP" + añoActual) + 1;
        String folio = String.format("ASP%s%06d", añoActual, consecutivo);
        persona.setFolio(folio);

        // 3️⃣ Activar el usuario por defecto
        persona.setActivo(true);

        // 4️⃣ Asignar el rol "Aspirante" automáticamente
        Rol rolAspirante = rolRepository.findByDesRol("Aspirante");
        Set<Rol> roles = new HashSet<>();
        roles.add(rolAspirante);
        persona.setRoles(roles);

        // 5️⃣ Guardar la persona en la base de datos
        personaRepository.save(persona);

        // 6️⃣ Mostrar confirmación
        model.addAttribute("folio", folio);
        model.addAttribute("curp", persona.getCurp());
        return "registro_exitoso";
    }
}
