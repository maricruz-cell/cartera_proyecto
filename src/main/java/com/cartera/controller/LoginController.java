
package com.cartera.controller;

import com.cartera.dto.PerfilAspiranteDTO;
import com.cartera.service.PerfilAspiranteService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final PerfilAspiranteService perfilAspiranteService;

    // ESTA ES LA PANTALLA INICIAL
    @GetMapping("/")
    public String inicioPerfil() {
        return "perfil-ingreso";
    }


    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "perfil", required = false) String perfil,
            Model model
    ) {
        // Si NO trae parámetro perfil, NO dejar ver login
        if (perfil == null) {
            return "redirect:/";
        }

        model.addAttribute("perfilSeleccionado", perfil);
        return "login";
    }



    @GetMapping("/admin/menu")
    public String adminMenu() { return "admin/menu"; }

    @GetMapping("/aspirante/menu")
    public String aspiranteMenu(Model model, Authentication authentication) {

        String username = authentication.getName();

        PerfilAspiranteDTO perfil = perfilAspiranteService.obtenerPerfil(username);

        if (perfil != null) {
            model.addAttribute("nombreAspirante", perfil.getNombreCompleto());
            model.addAttribute("fotoAspirante", perfil.getRutaFoto());
        } else {
            model.addAttribute("nombreAspirante", "Aspirante");
            model.addAttribute("fotoAspirante", "/img/icon-user.png");
        }

        return "aspirante/menu";
    }

    @GetMapping("/reclutamiento/menu")
    public String reclutamientoMenu() { return "reclutamiento/menu"; }

    @GetMapping("/unidad/menu")
    public String unidadMenu() { return "unidad/menu"; }
}
