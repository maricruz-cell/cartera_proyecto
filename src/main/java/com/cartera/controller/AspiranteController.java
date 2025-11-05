package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/aspirante")
public class AspiranteController {

    @GetMapping("/menu")
    public String menu() {
        return "aspirante/menu_aspirante";
    }
}
