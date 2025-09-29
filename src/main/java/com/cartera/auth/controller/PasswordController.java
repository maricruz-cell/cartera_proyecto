package com.cartera.auth.controller;

import com.cartera.auth.service.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordController {

    private final AuthService authService;

    public PasswordController(AuthService authService) {
        this.authService = authService;
    }

    // Mostrar formulario de cambio de contraseña
    @GetMapping("/cambiar-password")
    public String mostrarFormulario() {
        return "cambiar-password"; // templates/cambiar-password.html
    }

    // Procesar cambio de contraseña
    @PostMapping("/cambiar-password")
    public String cambiarPassword(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam String nuevaPassword,
                                  Model model) {
        try {
            String curp = userDetails.getUsername(); // el username es la CURP
            authService.actualizarPassword(curp, nuevaPassword);

            model.addAttribute("msg", "Contraseña actualizada correctamente");
            return "redirect:/home"; // redirigir al home
        } catch (Exception e) {
            model.addAttribute("error", "Hubo un error al cambiar la contraseña");
            return "cambiar-password";
        }
    }
}
