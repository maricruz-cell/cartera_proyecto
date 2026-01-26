package com.cartera.controller.reclutamiento;

import com.cartera.service.reclutamiento.RequisicionAspiranteService;
import com.cartera.service.reclutamiento.UnidadRequisicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reclutamiento")
@RequiredArgsConstructor
public class UnidadRequisicionController {

    private final UnidadRequisicionService service;
    private final RequisicionAspiranteService requisicionAspiranteService;

    // LISTADO
    @GetMapping("/requisicion-listado")
    public String listado(@RequestParam(defaultValue = "registradas") String tipo, Model model) {

        model.addAttribute("tipo", tipo);

        if ("cubiertas".equalsIgnoreCase(tipo)) {
            model.addAttribute("lista", service.listarCubiertas());
            model.addAttribute("titulo", "Vacantes cubiertas");
        } else {
            model.addAttribute("lista", service.listarRegistradas());
            model.addAttribute("titulo", "Requisiciones registradas");
        }

        model.addAttribute("estatusCatalogo", service.obtenerCatalogoEstatus());
        return "reclutamiento/requisiciones-listado";
    }

    // DETALLE
    @GetMapping("/requisicion-detalle")
    public String detalle(@RequestParam Long id, Model model) {
        model.addAttribute("requisicion", service.detalle(id));
        return "reclutamiento/requisicion-detalle";
    }

    // CAMBIAR ESTATUS
    @PostMapping("/requisicion-estatus")
    public String cambiarEstatus(@RequestParam Long idRequisicion, @RequestParam Integer estatus) {
        service.cambiarEstatusPlaza(idRequisicion, estatus);
        return "redirect:/reclutamiento/requisicion-listado";
    }

    // ASPIRANTES (proponer/asignar/seleccionar)
    @GetMapping("/requisicion-aspirantes")
    public String aspirantes(
            @RequestParam Long id,
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(required = false, defaultValue = "sugeridos") String modo,
            Model model
    ) {
        var requisicion = service.detalle(id);

        model.addAttribute("requisicion", requisicion);
        model.addAttribute("idRequisicion", id);
        model.addAttribute("q", q);
        model.addAttribute("modo", modo);

        // ✅ SIEMPRE mostrar ligados
        model.addAttribute("ligados", requisicionAspiranteService.ligados(id));

        // ✅ SOLO si NO está cubierta -> mostrar buscador/lista
        if (!requisicion.isCubierta()) {
            if ("todos".equalsIgnoreCase(modo)) {
                model.addAttribute("aspirantes", requisicionAspiranteService.buscarTodos(id, q));
            } else {
                model.addAttribute("aspirantes", requisicionAspiranteService.buscarSugeridos(id, q));
            }
        }

        return "reclutamiento/requisicion-aspirantes";
    }

    @PostMapping("/requisicion-aspirantes/proponer")
    public String proponer(@RequestParam Long idRequisicion, @RequestParam Long idPersona) {
        requisicionAspiranteService.proponer(idRequisicion, idPersona);
        return "redirect:/reclutamiento/requisicion-aspirantes?id=" + idRequisicion;
    }

    @PostMapping("/requisicion-aspirantes/asignar")
    public String asignar(@RequestParam Long idRequisicion, @RequestParam Long idPersona) {
        requisicionAspiranteService.asignar(idRequisicion, idPersona);
        return "redirect:/reclutamiento/requisicion-aspirantes?id=" + idRequisicion;
    }

    @PostMapping("/requisicion-aspirantes/seleccionar")
    public String seleccionar(@RequestParam Long idRequisicion, @RequestParam Long idPersona) {
        requisicionAspiranteService.seleccionar(idRequisicion, idPersona);
        return "redirect:/reclutamiento/requisicion-aspirantes?id=" + idRequisicion;
    }

    @PostMapping("/requisicion-aspirantes/quitar")
    public String quitar(@RequestParam Long idRequisicion, @RequestParam Long idPersona) {
        requisicionAspiranteService.quitar(idRequisicion, idPersona);
        return "redirect:/reclutamiento/requisicion-aspirantes?id=" + idRequisicion;
    }
}
