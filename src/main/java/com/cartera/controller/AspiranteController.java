package com.cartera.controller;

import com.cartera.dto.*;

import com.cartera.model.CatCompetencias;
import com.cartera.model.CatCompetencias;
import com.cartera.model.DtDocumentosAspirante;
import com.cartera.model.DtPersona;
import com.cartera.model.Persona;

import com.cartera.repository.*;

import com.cartera.service.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cartera.dto.CompetenciasDTO;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequiredArgsConstructor
public class AspiranteController {

    private final PersonaRepository sbRepo;
    private final DtPersonaRepository dtRepo;
    private final PersonaPerfilService personaPerfilService;
    private final EscolaridadService escolaridadService;
    private final CatEstadosRepository catEstadosRepository;
    private final CatNacionalidadRepository catNacionalidadRepository;
    private final CatMunicipiosRepository catMunicipiosRepository;
    private final CatLocalidadesRepository catLocalidadesRepository;

    private final JavaMailSender mailSender;
    private final EmailService emailService;

    // Catálogos de escolaridad
    private final CatEstudioRepository catEstudioRepo;
    private final CatGradoRepository catGradoRepo;
    private final CatLicenciaturaRepository catLicRepo;
    private final CatInstitucionRepository catInstRepo;
    private final CatModalidadRepository catModalRepo;

    // Experiencia laboral
    private final ExperienciaLaboralService experienciaLaboralService;
    private final CatEmpleoRepository catEmpleoRepo;
    private final CatSectorGeneralRepository catSectorGeneralRepo;
    private final CatSectorRepository catSectorRepo;
    private final CatLaboralRepository catLaboralRepo;
    private final CatSueldoRepository catSueldoRepo;
    private final CatTipoContratacionRepository catTipoContratacionRepo;
    private final CatMotivoSeparacionRepository catMotivoSepRepo;


    private final CatAreaCapacitacionRepository catAreaCapacitacionRepo;
    private final CatCursoRepository catCursoRepo;
    private final CatInstitucionRepository catInstitucionRepo;
    private final CatDocumentoRepository catDocumentoRepo;
    private final CatHorasCursoRepository catHorasCursoRepo;
    private final VwCapacitacionCompletaRepository vwCapacitacionRepo;

    private final CapacitacionService capacitacionService;

    // Conocimientos Específicos
    private final ConocimientosService conocimientosService;
    private final CatIdiomasRepository catIdiomasRepo;
    private final CatInformaticaRepository catInformaticaRepo;

    //Competencias
    private final CatCompetenciasRepository catCompetenciasRepo;
    private final CompetenciasService competenciasService;

    //CargarDocumentos

    private final CatTipoDocumentoRepository catTipoDocumentoRepo;
    private final DtDocumentoAspiranteRepository documentosRepo;
    private final DocumentoService documentoService;

    // =============== CABECERA Y SIDEBAR ===============
    private void cargarDatosAspirante(Model model, Authentication auth) {
        model.addAttribute("nombreAspirante", auth.getName());
        model.addAttribute("fotoAspirante", "/img/aspirante/icon-user.png");
    }

    // =============== DATOS PERSONALES ===============
    @GetMapping("/aspirante/datos-personales")
    public String datosPersonales(Model model, Authentication auth) {
        // Cargar catálogo de nacionalidades
        model.addAttribute("nacionalidades", catNacionalidadRepository.findAll());
        model.addAttribute("estados", catEstadosRepository.findAll());



        cargarDatosAspirante(model, auth);
        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("persona", perfil);
        return "aspirante/datos-personales";
    }

    @PostMapping("/aspirante/datos-personales/guardar")
    public String guardarPerfil(@ModelAttribute("persona") PersonaPerfilDTO dto,
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
        dt.setIdMunicipio(dto.getIdMunicipio());
        dt.setIdLocalidad(dto.getIdLocalidad());
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

    // ===================== ESCOLARIDAD =====================
    private void cargarCatalogosEscolaridad(Model model) {
        model.addAttribute("estudios", catEstudioRepo.findAll());
        model.addAttribute("grados", catGradoRepo.findAll());
        model.addAttribute("licenciaturas", catLicRepo.findAll());
        model.addAttribute("instituciones", catInstRepo.findAll());
        model.addAttribute("modalidades", catModalRepo.findAll());
    }

    @GetMapping("/aspirante/escolaridad")
    public String escolaridad(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosEscolaridad(model);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        model.addAttribute("listaEscolaridad", escolaridadService.listarPorPersona(idPersona));

        EscolaridadDTO dto = new EscolaridadDTO();
        dto.setIdPersona(idPersona);

        model.addAttribute("escolaridadForm", dto);

        return "aspirante/escolaridad";
    }

    @PostMapping("/aspirante/escolaridad/guardar")
    public String guardarEscolaridad(@ModelAttribute("escolaridadForm") EscolaridadDTO dto) {
        escolaridadService.guardar(dto);
        return "redirect:/aspirante/escolaridad";
    }

    @GetMapping("/aspirante/escolaridad/editar/{id}")
    public String editarEscolaridad(@PathVariable("id") Integer id,
                                    Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosEscolaridad(model);

        EscolaridadDTO dto = escolaridadService.buscarPorId(id);
        model.addAttribute("escolaridadForm", dto);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("listaEscolaridad", escolaridadService.listarPorPersona(perfil.getIdPersona()));

        model.addAttribute("mostrarForm", true);

        return "aspirante/escolaridad";
    }

    @GetMapping("/aspirante/escolaridad/eliminar/{id}")
    public String eliminarEscolaridad(@PathVariable("id") Integer id) {
        escolaridadService.eliminar(id);
        return "redirect:/aspirante/escolaridad";
    }

    // ===================== EXPERIENCIA LABORAL =====================
    // Cargar catálogos de experiencia laboral
    private void cargarCatalogosExperiencia(Model model) {
        model.addAttribute("empleos", catEmpleoRepo.findAll());
        model.addAttribute("sectoresGenerales", catSectorGeneralRepo.findAll());
        model.addAttribute("sectoresPublicos", catSectorRepo.findAll());
        model.addAttribute("areasLaborales", catLaboralRepo.findAll());
        model.addAttribute("sueldos", catSueldoRepo.findAll());
        model.addAttribute("tiposContratacion", catTipoContratacionRepo.findAll());
        model.addAttribute("motivosSeparacion", catMotivoSepRepo.findAll());
    }


    @GetMapping("/aspirante/experiencia-laboral")
    public String experienciaLaboral(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosExperiencia(model);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        model.addAttribute("listaExperiencia",
                experienciaLaboralService.listarPorPersona(idPersona));

        ExperienciaLaboralDTO dto = new ExperienciaLaboralDTO();
        dto.setIdPersona(idPersona);

        model.addAttribute("experienciaForm", dto);

        return "aspirante/experiencia-laboral";
    }

    @PostMapping("/aspirante/experiencia-laboral/guardar")
    public String guardarExperiencia(@ModelAttribute("experienciaForm") ExperienciaLaboralDTO dto) {
        experienciaLaboralService.guardar(dto);
        return "redirect:/aspirante/experiencia-laboral";
    }

    @GetMapping("/aspirante/experiencia-laboral/editar/{id}")
    public String editarExperiencia(@PathVariable("id") Integer idExp,
                                    Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosExperiencia(model);

        ExperienciaLaboralDTO dto = experienciaLaboralService.buscarPorId(idExp);
        model.addAttribute("experienciaForm", dto);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("listaExperiencia",
                experienciaLaboralService.listarPorPersona(perfil.getIdPersona()));

        model.addAttribute("mostrarForm", true);

        return "aspirante/experiencia-laboral";
    }

    @GetMapping("/aspirante/experiencia-laboral/eliminar/{id}")
    public String eliminarExperiencia(@PathVariable("id") Integer id) {
        experienciaLaboralService.eliminar(id);
        return "redirect:/aspirante/experiencia-laboral";
    }


    // --------- CAPACITACIÓN ---------

    // ===================== CATÁLOGOS DE CAPACITACIÓN =====================
    private void cargarCatalogosCapacitacion(Model model) {
        model.addAttribute("areasCapacitacion", catAreaCapacitacionRepo.findAll());
        model.addAttribute("tiposCurso", catCursoRepo.findAll());
        model.addAttribute("institucionesCapacitacion", catInstitucionRepo.findAll());
        model.addAttribute("documentosCapacitacion", catDocumentoRepo.findAll());
        model.addAttribute("horasCurso", catHorasCursoRepo.findAll());

    }

    // ===================== LISTAR CAPACITACIÓN =====================
    @GetMapping("/aspirante/capacitacion")
    public String verCapacitacion(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosCapacitacion(model);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        model.addAttribute("listaCapacitacion",
                vwCapacitacionRepo.findByIdPersona(idPersona));

        // NECESARIO PARA QUE NO TRUENE THYMELEAF
        CapacitacionDTO dto = new CapacitacionDTO();
        dto.setIdPersona(idPersona);
        model.addAttribute("capacitacionForm", dto);

        return "aspirante/capacitacion";
    }


    // ===================== GUARDAR CAPACITACIÓN =====================
    @PostMapping("/aspirante/capacitacion/guardar")
    public String guardarCapacitacion(
            @ModelAttribute("capacitacionForm") CapacitacionDTO dto,
            Authentication auth) {

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        dto.setIdPersona(perfil.getIdPersona());

        capacitacionService.guardarCapacitacion(dto);

        return "redirect:/aspirante/capacitacion";
    }


    // ===================== EDITAR CAPACITACIÓN =====================
    @GetMapping("/aspirante/capacitacion/editar/{id}")
    public String editarCapacitacion(@PathVariable("id") Integer idCap,
                                     Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);
        cargarCatalogosCapacitacion(model);

        // DTO existente
        CapacitacionDTO dto = capacitacionService.buscarPorId(idCap);
        model.addAttribute("capacitacionForm", dto);

        // Persona actual
        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        // Lista actualizada
        model.addAttribute("listaCapacitacion",
                capacitacionService.listarPorPersona(idPersona));

        model.addAttribute("mostrarForm", true);

        return "aspirante/capacitacion";
    }

    // ===================== ELIMINAR CAPACITACIÓN =====================
    @GetMapping("/aspirante/capacitacion/eliminar/{id}")
    public String eliminarCapacitacion(@PathVariable("id") Integer id) {

        capacitacionService.eliminar(id);

        return "redirect:/aspirante/capacitacion";
    }

    //-------------Conocimientos________________


    // --------- CATÁLOGOS DE CONOCIMIENTOS ESPECÍFICOS ---------
    // ===================== CONOCIMIENTOS ESPECÍFICOS =====================

    // Cargar catálogos generales:
    private void cargarCatalogosConocimientos(Model model) {
        model.addAttribute("catalogoIdiomas", catIdiomasRepo.findAll());
        model.addAttribute("catalogoInformatica", catInformaticaRepo.findAll());
        model.addAttribute("niveles", List.of("BÁSICO", "INTERMEDIO", "AVANZADO"));
    }

    @GetMapping("/aspirante/competencias")
    public String verCompetencias(Model model, Authentication auth) {

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        // Catálogos
        model.addAttribute("competencias", catCompetenciasRepo.findByTipo("C"));
        model.addAttribute("habilidades", catCompetenciasRepo.findByTipo("H"));

        // IDs seleccionados que tiene la persona
        List<Long> seleccionados = competenciasService.cargar(idPersona);
        model.addAttribute("seleccionados", seleccionados);

        return "aspirante/competencias";
    }

    @PostMapping("/aspirante/competencias/guardar")
    public String guardarCompetencias(
            @RequestParam("idCompetencia") List<Long> idsSeleccionados,
            Authentication auth) {

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        competenciasService.guardarLista(idPersona, idsSeleccionados);

        return "redirect:/aspirante/competencias";
    }



    //--------------Cargardocumentos-----------
    @GetMapping("/aspirante/cargardocumentos")
    public String cargarDocumentos(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        // 1. Catálogo base
        var tipos = catTipoDocumentoRepo.findAll();
        model.addAttribute("tiposDocumento", tipos);

        // 2. Documentos subidos
        var documentos = documentosRepo.findByIdPersona(idPersona);
        model.addAttribute("documentos", documentos);

        // 3. Convertir lista -> mapa { idTipoDocumento : documento }
        Map<Long, DtDocumentosAspirante> mapDocs = new HashMap<>();
        for (DtDocumentosAspirante d : documentos) {
            mapDocs.put(d.getIdTipoDocumento(), d);
        }

        model.addAttribute("mapDocs", mapDocs);

        return "aspirante/cargardocumentos";
    }

    // ░░ GUARDAR DOCUMENTOS ░░
    @PostMapping("/aspirante/cargardocumentos/guardar")
    public String guardarDocumento(
            @RequestParam("tipoDocumento") Long idTipoDocumento,
            @RequestParam("archivo") MultipartFile archivo,
            Authentication auth
    ) throws Exception {

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        documentoService.guardarDocumento(idPersona, idTipoDocumento, archivo);

        return "redirect:/aspirante/cargardocumentos?success=1";
    }

    // ░░ CARGAR DOCUMENTOS (vista profesional) ░░




    //--------------CAMBIAR CONTRASEÑA-----------------------------
    //--------------CAMBIAR CONTRASEÑA-----------------------------

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*@Autowired
    private EmailService emailService;   // servicio para enviar correos*/


    @GetMapping("/aspirante/cambiar-contrasena")
    public String cambiarContrasena(Model model, Authentication auth) {

        cargarDatosAspirante(model, auth);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("usuario", perfil.getCurp());
        model.addAttribute("nombre", perfil.getNombre());

        return "aspirante/cambiar-contrasena";
    }


    @PostMapping("/aspirante/cambiar-contrasena")
    public String actualizarContrasena(
            @RequestParam("actual") String actual,
            @RequestParam("nueva") String nueva,
            @RequestParam("confirmar") String confirmar,
            Authentication auth,
            Model model) {

        cargarDatosAspirante(model, auth);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        Long idPersona = perfil.getIdPersona();

        Persona persona = sbRepo.findById(idPersona)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // ========== VALIDAR CONTRASEÑA ACTUAL ==========
        if (!passwordEncoder.matches(actual, persona.getContrasena_base64())) {
            model.addAttribute("error", "La contraseña actual es incorrecta.");
            return "aspirante/cambiar-contrasena";
        }

        // ========== VALIDAR NUEVA CONTRASEÑA ==========
        if (!nueva.equals(confirmar)) {
            model.addAttribute("error", "Las nuevas contraseñas no coinciden.");
            return "aspirante/cambiar-contrasena";
        }

        if (!validarPasswordSegura(nueva)) {
            model.addAttribute("error", "La nueva contraseña no cumple con los requisitos de seguridad.");
            return "aspirante/cambiar-contrasena";
        }

        // ========== ACTUALIZAR EN BD ==========
        persona.setContrasena_base64(passwordEncoder.encode(nueva));
        sbRepo.save(persona);

        // ========== ENVIAR CORREO ==========
        try {
            emailService.enviarCorreo(
                    persona.getCorreo(),
                    "Cambio de contraseña",
                    "Hola " + persona.getNombre() + ",\n\n" +
                            "Tu contraseña ha sido actualizada correctamente.\n\n" +
                            "Si no realizaste este cambio, comunícate inmediatamente con soporte."
            );
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

        model.addAttribute("success", "Tu contraseña se actualizó correctamente.");
        return "aspirante/cambiar-contrasena";
    }


    // ------------ VALIDACIÓN DE CONTRASEÑA SEGURA ------------
    private boolean validarPasswordSegura(String pass) {

        boolean longitud = pass.length() >= 8;
        boolean mayus = pass.matches(".*[A-Z].*");
        boolean minus = pass.matches(".*[a-z].*");
        boolean numero = pass.matches(".*[0-9].*");
        boolean simbolo = pass.matches(".*[^A-Za-z0-9].*");

        return longitud && mayus && minus && numero && simbolo;
    }


    //------------------ELIMINAR CUENTA --------------------------------

    // ========== ELIMINAR USUARIO ==========
    @GetMapping("/aspirante/eliminar-cuenta")
    public String eliminarCuenta(Model model, Authentication auth) {
        cargarDatosAspirante(model, auth);

        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        model.addAttribute("usuarioEliminar", perfil);

        return "aspirante/eliminar-cuenta";
    }

    @PostMapping("/aspirante/eliminar-cuenta")
    public String eliminarCuentaPost(
            @RequestParam("motivo") String motivo,
            @RequestParam(value = "confirmado", required = false) String confirmado,
            Authentication auth
    ) {

        // Validar que marcó la casilla
        if (confirmado == null) {
            return "redirect:/aspirante/eliminar-cuenta?error=Debes confirmar que entiendes que la acción es irreversible";
        }

        // Validar motivo
        if (motivo == null || motivo.trim().isEmpty()) {
            return "redirect:/aspirante/eliminar-cuenta?error=Debes escribir el motivo de la eliminación";
        }

        // Obtener persona a partir de la CURP (username)
        PersonaPerfilDTO perfil = personaPerfilService.cargarPerfilPorCurp(auth.getName());
        if (perfil == null) {
            System.out.println("❌ No se encontró perfil para: " + auth.getName());
            return "redirect:/aspirante/eliminar-cuenta?error=No se encontró el usuario";
        }

        Persona persona = sbRepo.findById(perfil.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Persona no encontrada en sb_persona"));

        // Enviar correo
        try {
            mailSender.send(enviarCorreoEliminacion(persona.getCorreo(), motivo));
        } catch (Exception e) {
            System.out.println("No se pudo enviar correo: " + e.getMessage());
        }

        /*/ Eliminación lógica
        persona.setActivo(false);
        sbRepo.save(persona);*/
        sbRepo.delete(persona);


        // Cerrar sesión
        return "redirect:/logout";
    }


    // ----- CORREO -----
    private SimpleMailMessage enviarCorreoEliminacion(String correo, String motivo) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(correo);
        msg.setSubject("Notificación: Cuenta Eliminada");
        msg.setText(
                "Su cuenta ha sido eliminada del sistema.\n\n" +
                        "Motivo registrado:\n" + motivo + "\n\n" +
                        "Si considera que esto es un error, contacte a soporte."
        );
        return msg;
    }
}

