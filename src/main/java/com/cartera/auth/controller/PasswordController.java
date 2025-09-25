package com.cartera.auth.controller;

import com.cartera.auth.service.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordController {

    private final AuthService authService;

    public PasswordController(AuthService authService) {
        this.authService = authService;
    }

    // Mostrar formulario de cambio de contraseña
    @GetMapping("/cambiar-password")
    public String formCambiarPassword() {
        return "cambiar-password"; // templates/cambiar-password.html
    }

    // Procesar cambio de contraseña
    @PostMapping("/cambiar-password")
    public String cambiarPassword(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam String nuevaPassword,
                                  Model model) {
        try {
            String username = userDetails.getUsername(); // aquí es el CURP
            authService.actualizarPassword(username, nuevaPassword);

            model.addAttribute("msg", "Contraseña actualizada correctamente. Inicia sesión con tu nueva contraseña.");
            return "redirect:/login?passwordChanged"; // redirige al login
        } catch (Exception e) {
            model.addAttribute("error", "Hubo un error al cambiar la contraseña: " + e.getMessage());
            return "cambiar-password";
        }
    }
}
