package com.cartera.controller.unidad;

import com.cartera.model.DtRequisicion;
import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import com.cartera.service.unidad.RequisicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/unidad")
public class RequisicionUnidadController {

    private final PersonaRepository personaRepo;
    private final RequisicionService requisicionService;

    // ===============================
    // PASO 1 – DEPENDENCIA
    // ===============================
    @GetMapping("/dependencia")
    public String dependencia(
            @AuthenticationPrincipal User user,
            Model model
    ) {

        // 🔐 Persona logueada (desde SPRING SECURITY)
        Persona persona = personaRepo.findByCorreo(user.getUsername())
                .orElseThrow(() ->
                        new RuntimeException("No existe persona con ese correo")
                );

        // 📄 Requisición ACTIVA / MÁS RECIENTE de esa persona
        DtRequisicion requisicion =
                requisicionService.obtenerPorPersona(persona.getId_persona());

        // 📤 Se manda AL HTML (NO lista)
        model.addAttribute("requisicion", requisicion);

        return "unidad/requisicion-dependencia";
    }
}
