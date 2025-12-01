package com.cartera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.cartera.service.VacantesService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
@RequiredArgsConstructor
public class ReclutamientoController {

    private String obtenerNombreUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();  // regresa el username logueado
    }


    private final VacantesService service;

    // ====== VACANTES REGISTRADAS ======
    @GetMapping("/reclutamiento/vacantes-registradas")
    public String verVacantes(Model model) {

        String usuario = obtenerNombreUsuario();
        model.addAttribute("usuario", usuario);
        model.addAttribute("vacantes", service.listarVacantes());

        return "reclutamiento/vacantes-registradas";
    }




    // ====== VACANTES CUBIERTAS ======
    @GetMapping("/reclutamiento/cubiertas")
    public String vacantesCubiertas() {
        return "reclutamiento/vacantes_cubiertas";
    }

    // ====== ASPIRANTES REGISTRADOS ======
    @GetMapping("/reclutamiento/aspirantes")
    public String aspirantesRegistrados() {
        return "reclutamiento/aspirantes_registrados";
    }

    // ====== ASPIRANTES NO ACTIVOS ======
    @GetMapping("/reclutamiento/noactivos")
    public String aspirantesNoActivos() {
        return "reclutamiento/aspirantes_no_activos";
    }

    // ====== ESTADISTICOS ======
    @GetMapping("/reclutamiento/estadisticos")
    public String estadisticos() {
        return "reclutamiento/estadisticos";
    }

    // ====== CATÁLOGOS ======
    @GetMapping("/reclutamiento/catalogos")
    public String catalogos() {
        return "reclutamiento/catalogos";
    }

    // ====== COORDINADORES ======
    @GetMapping("/reclutamiento/coordinadores")
    public String coordinadores() {
        return "reclutamiento/coordinadores";
    }

    // ====== REPORTES ======
    @GetMapping("/reclutamiento/reportes")
    public String reportes() {
        return "reclutamiento/reportes";
    }

    // ====== CAMBIAR CONTRASEÑA ======
    @GetMapping("/reclutamiento/contrasena")
    public String contrasena() {
        return "reclutamiento/cambiar_contrasena";
    }

    // ====== RECORDATORIO ======
    @GetMapping("/reclutamiento/recordatorio")
    public String recordatorio() {
        return "reclutamiento/recordatorio";
    }
}
