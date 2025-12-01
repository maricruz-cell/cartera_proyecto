
package com.cartera.controller;

import com.cartera.model.RegistroDto;
import com.cartera.service.RegistroService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.cartera.repository.CatEstadoCivilRepository;
import com.cartera.repository.CatSexoRepository;
import com.cartera.repository.CatNacionalidadRepository;
import com.cartera.repository.CatEstadosRepository;
@Controller
@RequiredArgsConstructor
public class RegistroController {

    private final RegistroService registroService;
    private final CatSexoRepository catSexoRepository;
    private final CatEstadoCivilRepository catEstadoCivilRepository;
    private final CatNacionalidadRepository catNacionalidadRepository;
    private final CatEstadosRepository catEstadosRepository;


    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("registroDto", new RegistroDto());
        model.addAttribute("sexos", catSexoRepository.findAll());
        model.addAttribute("estadosCiviles", catEstadoCivilRepository.findAll());
        model.addAttribute("nacionalidades", catNacionalidadRepository.findAll());
        model.addAttribute("estados", catEstadosRepository.findAll());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarAspirante(@ModelAttribute RegistroDto dto,
                                     HttpServletRequest request,
                                     Model model) {
        try {
            String appBaseUrl = request.getRequestURL().toString()
                    .replace(request.getRequestURI(), "");
            registroService.registrarAspirante(dto, appBaseUrl);
            model.addAttribute("correo", dto.getCorreo());
            return "registro_exitoso";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    @GetMapping("/activar")
    public String activarCuenta(@RequestParam("token") String token, Model model) {
        boolean ok = registroService.activarCuenta(token);
        model.addAttribute("resultado", ok);
        return "activacion_resultado";
    }
}
