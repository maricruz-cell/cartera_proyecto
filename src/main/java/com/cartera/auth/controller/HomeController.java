
package com.cartera.auth.controller;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.service.AuthService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cartera.auth.model.Direccion;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final AuthService authService;

    public HomeController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/home")
    public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        // El CURP es el username
        String curp = userDetails.getUsername();
        Usuario usuario = authService.findByUsername(curp);

        model.addAttribute("usuario", usuario);
        return "home"; // templates/home.html
    }
    @PostMapping("/update-datos")
    public String actualizarDatos(@ModelAttribute Usuario usuario, Model model) {
        authService.actualizarUsuario(usuario);
        model.addAttribute("usuario", usuario);
        return "redirect:/home";
    }

    @PostMapping("/update-direccion")
    public String actualizarDireccion(@ModelAttribute Direccion direccion,
                                      @RequestParam String curp,
                                      Model model) {
        authService.actualizarDireccion(curp, direccion);
        model.addAttribute("usuario", authService.findByUsername(curp));
        return "home"; // vuelve al home con datos actualizados
    }


}
