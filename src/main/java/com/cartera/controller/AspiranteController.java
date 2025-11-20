package com.cartera.controller;

import com.cartera.dto.EscolaridadDTO;
import com.cartera.dto.PersonaPerfilDTO;
import com.cartera.model.DtPersona;
import com.cartera.model.Persona;
import com.cartera.repository.*;
import com.cartera.service.PersonaPerfilService;
import com.cartera.service.EscolaridadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import com.cartera.service.EscolaridadService;
import org.springframework.beans.factory.annotation.Autowired;


@Controller
@RequiredArgsConstructor
public class AspiranteController {

    private final PersonaRepository sbRepo;
    private final DtPersonaRepository dtRepo;
    private final PersonaPerfilService personaPerfilService;
    private final EscolaridadService escolaridadService;

    // NUEVOS:

    private final CatEstudioRepository catEstudioRepo;

    private final CatGradoRepository catGradoRepo;

    private final CatLicenciaturaRepository catLicRepo;

    private final CatInstitucionRepository catInstRepo;

    private final CatModalidadRepository catModalRepo;

    // =============== CABECERA Y SIDEBAR ===============
    private void cargarDatosAspirante(Model model, Authentication auth) {
        model.addAttribute("nombreAspirante", auth.getName());
        model.addAttribute("fotoAspirante", "/img/aspirante/avatar-default.png");
    }

    // =============== GET: MOSTRAR PERFIL ===============
    @GetMapping("/aspirante/datos-personales")
    public String datosPersonales(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);

        String curpLogin = auth.getName();
        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(curpLogin);
        model.addAttribute("persona", perfil);

        return "aspirante/datos-personales";
    }

    // =============== POST: GUARDAR PERFIL ===============
    @PostMapping("/aspirante/datos-personales/guardar")
    public String guardarPerfil(
            @ModelAttribute("persona") PersonaPerfilDTO dto,
            @AuthenticationPrincipal User user) {

        Persona sb = sbRepo.findById(dto.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada"));

        DtPersona dt = dtRepo.findById(dto.getIdPersona())
                .orElse(new DtPersona());
        dt.setIdPersona(dto.getIdPersona());
        dt.setFechaNacimiento(dto.getFechaNacimiento());
        dt.setEdad(dto.getEdad());
        dt.setIdSexo(dto.getSexo());
        dt.setIdEstadoCivil(dto.getEstadoCivil());
        dt.setIdNacionalidad(dto.getNacionalidad());

        dt.setCalle(dto.getCalle());
        dt.setColonia(dto.getColonia());
        dt.setCodigoPostal(dto.getCodigoPostal());
        dt.setNumExterior(dto.getNumExterior());
        dt.setNumInterior(dto.getNumInterior());

        dt.setTelefono(dto.getTelefono());
        dt.setTelefonoRecados(dto.getTelefonoRecados());
        dt.setCorreoAlternativo(dto.getCorreoAlternativo());
        dt.setRedSocial(dto.getRedSocial());

        dt.setEsEmpleado(dto.getEsEmpleado());
        dt.setIdSueldo(dto.getSueldo());
        dt.setIdDisponibilidadHorario(dto.getDisponibilidadHorario());
        dt.setIdDisponibilidadViajar(dto.getDisponibilidadViajar());

        dtRepo.save(dt);

        return "redirect:/aspirante/datos-personales";
    }

    // ================== ESCOLARIDAD ==================

    // =========================
//       ESCOLARIDAD
// =========================


    // Cargar catálogos
    private void cargarCatalogosEscolaridad(Model model) {
        model.addAttribute("estudios", catEstudioRepo.findAll());
        model.addAttribute("grados", catGradoRepo.findAll());
        model.addAttribute("licenciaturas", catLicRepo.findAll());
        model.addAttribute("instituciones", catInstRepo.findAll());
        model.addAttribute("modalidades", catModalRepo.findAll());
    }


    // =========================
//   LISTAR ESCOLARIDAD
// =========================
    @GetMapping("/aspirante/escolaridad")
    public String escolaridad(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosEscolaridad(model);

        // Obtener persona logueada
        String curpLogin = auth.getName();
        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(curpLogin);

        Long idPersona = perfil.getIdPersona();

        // Lista de escolaridad
        model.addAttribute("listaEscolaridad", escolaridadService.listarPorPersona(idPersona));

        // Form vacío (para agregar)
        EscolaridadDTO dto = new EscolaridadDTO();
        dto.setIdPersona(idPersona);

        model.addAttribute("escolaridadForm", dto);

        return "aspirante/escolaridad";
    }



    // =========================
//      GUARDAR REGISTRO
// =========================
    @PostMapping("/aspirante/escolaridad/guardar")
    public String guardarEscolaridad(@ModelAttribute("escolaridadForm") EscolaridadDTO dto,
                                     Authentication auth) {

        escolaridadService.guardar(dto);
        return "redirect:/aspirante/escolaridad";
    }



    // =========================
//          EDITAR
// =========================
    @GetMapping("/aspirante/escolaridad/editar/{id}")
    public String editarEscolaridad(@PathVariable("id") Integer idEscolaridad,
                                    Model model,
                                    Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosEscolaridad(model);

        EscolaridadDTO dto = escolaridadService.buscarPorId(idEscolaridad);

        model.addAttribute("escolaridadForm", dto);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("listaEscolaridad", escolaridadService.listarPorPersona(perfil.getIdPersona()));

        // 🔥 IMPORTANTE → MOSTRAR FORMULARIO AL EDITAR
        model.addAttribute("mostrarForm", true);

        return "aspirante/escolaridad";
    }



    // =========================
//         ELIMINAR
// =========================
    @GetMapping("/aspirante/escolaridad/eliminar/{id}")
    public String eliminarEscolaridad(@PathVariable("id") Integer id) {

        escolaridadService.eliminar(id);
        return "redirect:/aspirante/escolaridad";
    }



    // ========= OTRAS SECCIONES QUE YA TENÍAS =========

    @GetMapping("/aspirante/experiencia-laboral")
    public String experienciaLaboral(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/experiencia-laboral";
    }

    @GetMapping("/aspirante/capacitacion")
    public String capacitacion(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/capacitacion";
    }

    @GetMapping("/aspirante/conocimientos")
    public String conocimientos(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/conocimientos";
    }

    @GetMapping("/aspirante/competencias")
    public String competencias(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/competencias";
    }

    @GetMapping("/aspirante/cambiar-contrasena")
    public String cambiarContrasena(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/cambiar-contrasena";
    }

    @GetMapping("/aspirante/eliminar-cuenta")
    public String eliminarcuenta(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);
        return "aspirante/eliminar-cuenta";
    }
}
