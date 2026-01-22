package com.cartera.controller.unidad;

import com.cartera.service.unidad.UnidadRequisicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/unidad")
@RequiredArgsConstructor
public class UnidadRequisicionController {

    private final UnidadRequisicionService service;

    // LISTADO
    @GetMapping("/requisicion-listado")
    public String listado(
            @RequestParam(defaultValue = "registradas") String tipo,
            Model model
    ) {
        model.addAttribute("tipo", tipo);

        if ("cubiertas".equalsIgnoreCase(tipo)) {
            model.addAttribute("lista", service.listarCubiertas());
            model.addAttribute("titulo", "Vacantes cubiertas");
        } else {
            model.addAttribute("lista", service.listarRegistradas());
            model.addAttribute("titulo", "Requisiciones registradas");
        }

        return "unidad/requisiciones-listado";
    }


    // 🔥 DETALLE (ESTA ES LA CLAVE)
    @GetMapping("/requisicion-detalle")
    public String detalle(@RequestParam Long id, Model model) {
        model.addAttribute("requisicion", service.detalle(id));
        return "unidad/requisicion-detalle";
    }
}
