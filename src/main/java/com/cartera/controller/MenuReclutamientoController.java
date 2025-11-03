package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reclutamiento")
public class MenuReclutamientoController {

    @GetMapping("/menu")
    public String menuReclutamiento() {
        return "menu_reclutamiento"; // archivo menu_reclutamiento.html
    }
}
