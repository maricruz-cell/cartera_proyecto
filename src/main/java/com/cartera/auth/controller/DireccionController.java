package com.cartera.auth.controller;

import com.cartera.auth.model.Direccion;
import com.cartera.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/direccion")
public class DireccionController {

    private final AuthService authService;

    public DireccionController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String formDireccion(Model model) {
        model.addAttribute("direccion", new Direccion());
        return "direccion"; // templates/direccion.html
    }

    @PostMapping
    public String guardarDireccion(@ModelAttribute Direccion direccion, @RequestParam String curp, Model model) {
        authService.actualizarDireccion(curp, direccion);
        model.addAttribute("msg", "Dirección guardada correctamente");
        return "redirect:/home";
    }
}
