package com.cartera.auth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/redirectByRole")
    public String redirectByRole(Authentication auth) {
        for (GrantedAuthority authority : auth.getAuthorities()) {
            String role = authority.getAuthority();
            switch (role) {
                case "ROLE_ADMINISTRADOR":
                    return "redirect:/admin/menu";
                case "ROLE_ASPIRANTE":
                    return "redirect:/aspirante/menu";
                case "ROLE_RECLUTAMIENTO":
                    return "redirect:/reclutamiento/menu";
                case "ROLE_UNIDAD":
                    return "redirect:/unidad/menu";
            }
        }
        return "redirect:/login?sinRol";
    }

    @GetMapping("/admin/menu")
    public String adminMenu() {
        return "menu-admin"; // menu-admin.html
    }

    @GetMapping("/aspirante/menu")
    public String aspiranteMenu() {
        return "menu-aspirante"; // menu-aspirante.html
    }

    @GetMapping("/reclutamiento/menu")
    public String reclutamientoMenu() {
        return "menu-reclutamiento"; // menu-reclutamiento.html
    }

    @GetMapping("/unidad/menu")
    public String unidadMenu() {
        return "menu-unidad"; // archivo: menu-unidad.html
    }
}
