package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unidad")
public class MenuUnidadController {

    @GetMapping("/menu")
    public String menuUnidad() {
        return "menu_unidad"; // archivo menu_unidad.html
    }
}
