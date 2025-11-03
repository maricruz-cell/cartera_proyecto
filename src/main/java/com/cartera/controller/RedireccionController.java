package com.cartera.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedireccionController {

    @GetMapping("/redirigir")
    public String redirigir(Authentication auth) {
        if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/menu";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_RECLUTAMIENTO"))) {
            return "redirect:/reclutamiento/menu";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_UNIDAD"))) {
            return "redirect:/unidad/menu";
        } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ASPIRANTE"))) {
            return "redirect:/aspirante/menu";
        } else {
            return "redirect:/login?error";
        }
    }
}
