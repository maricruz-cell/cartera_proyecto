package com.cartera.auth.controller;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import com.cartera.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar el login
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login"; // templates/login.html
    }

    // Mostrar formulario de registro (opcional)
    @GetMapping("/register")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register"; // templates/register.html
    }

    // Procesar registro
    @PostMapping("/register")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }

    // Página de inicio después del login
    @GetMapping("/home")
    public String home() {
        return "home"; // templates/home.html
    }
}
