package com.cartera.controller.reclutamiento;

import com.cartera.service.reclutamiento.ReclutamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reclutamiento")
public class ReclutamientoController {

    private final ReclutamientoService service;

    @GetMapping("/propuestos/{id}")
    public String propuestos(@PathVariable Long id, Model model) {
        model.addAttribute("lista", service.obtenerAspirantesPropuestos(id));
        return "reclutamiento/propuestos";
    }

    @GetMapping("/rechazados/{id}")
    public String rechazados(@PathVariable Long id, Model model) {
        model.addAttribute("lista", service.obtenerAspirantesRechazados(id));
        return "reclutamiento/rechazados";
    }
}
