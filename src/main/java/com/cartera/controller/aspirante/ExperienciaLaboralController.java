package com.cartera.controller.aspirante;

import com.cartera.dto.ExperienciaLaboralDTO;
import com.cartera.dto.PersonaPerfilDTO;
import com.cartera.repository.*;
import com.cartera.service.ExperienciaLaboralService;
import com.cartera.service.PersonaPerfilService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/aspirante/experiencia-laboral")
public class ExperienciaLaboralController {

    private final SharedAspiranteController shared;
    private final PersonaPerfilService perfilService;

    private final ExperienciaLaboralService experienciaService;

    private final CatEmpleoRepository catEmpleoRepo;
    private final CatSectorGeneralRepository catSectorGeneralRepo;
    private final CatSectorRepository catSectorRepo;
    private final CatLaboralRepository catLaboralRepo;
    private final CatSueldoRepository catSueldoRepo;
    private final CatTipoContratacionRepository catTipoContratacionRepo;
    private final CatMotivoSeparacionRepository catMotivoSepRepo;

    private void cargarCatalogos(Model model) {
        model.addAttribute("empleos", catEmpleoRepo.findAll());
        model.addAttribute("sectoresGenerales", catSectorGeneralRepo.findAll());
        model.addAttribute("sectoresPublicos", catSectorRepo.findAll());
        model.addAttribute("areasLaborales", catLaboralRepo.findAll());
        model.addAttribute("sueldos", catSueldoRepo.findAll());
        model.addAttribute("tiposContratacion", catTipoContratacionRepo.findAll());
        model.addAttribute("motivosSeparacion", catMotivoSepRepo.findAll());
    }

    @GetMapping
    public String experiencia(Model model, Authentication auth) {

        shared.cargarDatosAspirante(model, auth);
        cargarCatalogos(model);

        PersonaPerfilDTO perfil = perfilService.cargarPerfil(auth.getName());
        Long idPersona = perfil.getIdPersona();

        model.addAttribute("listaExperiencia", experienciaService.listarPorPersona(idPersona));

        ExperienciaLaboralDTO dto = new ExperienciaLaboralDTO();
        dto.setIdPersona(idPersona);

        model.addAttribute("experienciaForm", dto);

        return "aspirante/experiencia-laboral";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("experienciaForm") ExperienciaLaboralDTO dto) {
        experienciaService.guardar(dto);
        return "redirect:/aspirante/experiencia-laboral";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model, Authentication auth) {

        shared.cargarDatosAspirante(model, auth);
        cargarCatalogos(model);

        ExperienciaLaboralDTO dto = experienciaService.buscarPorId(id);
        model.addAttribute("experienciaForm", dto);

        PersonaPerfilDTO perfil = perfilService.cargarPerfil(auth.getName());
        model.addAttribute("listaExperiencia", experienciaService.listarPorPersona(perfil.getIdPersona()));

        model.addAttribute("mostrarForm", true);

        return "aspirante/experiencia-laboral";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {
        experienciaService.eliminar(id);
        return "redirect:/aspirante/experiencia-laboral";
    }
}
