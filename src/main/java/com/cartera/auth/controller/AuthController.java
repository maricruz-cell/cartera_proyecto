package com.cartera.auth.controller;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Mostrar login
    @GetMapping("/login")
    public String loginForm() {
        return "login"; // templates/login.html
    }

    // Mostrar registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register"; // templates/register.html
    }

    // Procesar registro
    @PostMapping("/register")
    public String register(@ModelAttribute Usuario datos, Model model) {
        Usuario nuevo = authService.register(datos);

        // Mostrar CURP y contraseña temporal (NO el hash)
        model.addAttribute("usuario", nuevo.getCurp());
        model.addAttribute("password", nuevo.getTempPassword());

        return "credenciales"; // templates/credenciales.html
    }
}
