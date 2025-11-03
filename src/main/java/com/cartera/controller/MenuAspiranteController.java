package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aspirante")
public class MenuAspiranteController {

    @GetMapping("/menu")
    public String menuAspirante() {
        return "menu_aspirante"; // archivo menu_aspirante.html
    }
}
