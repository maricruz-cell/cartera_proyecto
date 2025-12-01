package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/unidad")
public class UnidadController {


    // Muestra la pantalla de Requisición de Personal
    @GetMapping("/requisicion")
    public String requisicionPersonal() {
        return "unidad/requisicion_personal";
    }
}
