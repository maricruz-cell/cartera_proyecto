package com.cartera.service;

import com.cartera.dto.ExperienciaLaboralDTO;
import com.cartera.model.DtExperienciaLaboral;
import com.cartera.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienciaLaboralService {

    private final DtExperienciaLaboralRepository experienciaRepo;

    private final CatEmpleoRepository             catEmpleoRepo;
    private final CatSectorGeneralRepository      catSectorGeneralRepo;
    private final CatSectorRepository             catSectorRepo;
    private final CatLaboralRepository            catLaboralRepo;
    private final CatSueldoRepository             catSueldoRepo;
    private final CatTipoContratacionRepository   catTipoContratacionRepo;
    private final CatMotivoSeparacionRepository   catMotivoSepRepo;

    // ================== GUARDAR / ACTUALIZAR ==================
    public void guardar(ExperienciaLaboralDTO dto) {

        DtExperienciaLaboral e = new DtExperienciaLaboral();

        // Si trae ID → edición
        if (dto.getIdExperienciaLaboral() != null) {
            e = experienciaRepo.findById(dto.getIdExperienciaLaboral())
                    .orElse(new DtExperienciaLaboral());
        }

        e.setIdPersona(dto.getIdPersona());

        // ------- FKs de la primera parte (Datos laborales) -------
        e.setIdTipoEmpleo(dto.getIdTipoEmpleo());
        e.setIdSectorGeneral(dto.getIdSectorGeneral());
        e.setIdSector(dto.getIdSector());
        e.setIdLaboral(dto.getIdLaboral());

        // ------- Texto de la primera parte -------
        e.setEmpresa(dto.getEmpresa());
        e.setPuesto(dto.getPuesto());
        e.setCampoExperiencia(dto.getAreaLaboralEspecifica());
        e.setFunciones(dto.getFunciones());

        // "Otra función desempeñada" la guardamos en OBSERVACIONES
        e.setObservaciones(dto.getOtraFuncion());

        // ------- Fechas y cálculo de años / meses -------
        e.setFechaInicio(dto.getFechaInicio());
        e.setFechaFin(dto.getFechaFin());

        if (dto.getFechaInicio() != null
                && dto.getFechaFin() != null
                && !dto.getFechaFin().isBefore(dto.getFechaInicio())) {

            Period p = Period.between(dto.getFechaInicio(), dto.getFechaFin());
            int totalMeses = p.getYears() * 12 + p.getMonths();
            int anios = totalMeses / 12;
            int meses = totalMeses % 12;

            e.setAnios(anios);
            e.setMeses(meses);

            dto.setAnios(anios);
            dto.setMeses(meses);
        } else {
            e.setAnios(dto.getAnios());
            e.setMeses(dto.getMeses());
        }

        // ------- Segunda parte (Descripción del empleo) -------
        e.setIdSueldo(dto.getIdSueldo());
        e.setIdTipoContratacion(dto.getIdTipoContratacion());
        e.setIdMotivoSeparacion(dto.getIdMotivoSeparacion());
        e.setMotivoSeparacion(dto.getMotivoSeparacion());
        e.setNivelPuesto(dto.getNivelPuesto());

        experienciaRepo.save(e);
    }

    // ================== LISTAR POR PERSONA ==================
    public List<ExperienciaLaboralDTO> listarPorPersona(Long idPersona) {
        return experienciaRepo.findByIdPersona(idPersona)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // ================== BUSCAR POR ID (EDITAR) ==================
    public ExperienciaLaboralDTO buscarPorId(Integer idExp) {
        DtExperienciaLaboral e = experienciaRepo.findById(idExp)
                .orElseThrow(() -> new RuntimeException("Experiencia no encontrada"));
        return convertirADTO(e);
    }

    // ================== ELIMINAR ==================
    public void eliminar(Integer id) {
        experienciaRepo.deleteById(id);
    }

    // ================== ENTIDAD → DTO ==================
    private ExperienciaLaboralDTO convertirADTO(DtExperienciaLaboral e) {

        ExperienciaLaboralDTO dto = new ExperienciaLaboralDTO();

        dto.setIdExperienciaLaboral(e.getIdExperienciaLaboral());
        dto.setIdPersona(e.getIdPersona());

        dto.setIdTipoEmpleo(e.getIdTipoEmpleo());
        dto.setIdSectorGeneral(e.getIdSectorGeneral());
        dto.setIdSector(e.getIdSector());
        dto.setIdLaboral(e.getIdLaboral());

        dto.setEmpresa(e.getEmpresa());
        dto.setPuesto(e.getPuesto());
        dto.setAreaLaboralEspecifica(e.getCampoExperiencia());
        dto.setFunciones(e.getFunciones());

        // OBSERVACIONES = "Otra función desempeñada"
        dto.setOtraFuncion(e.getObservaciones());

        dto.setFechaInicio(e.getFechaInicio());
        dto.setFechaFin(e.getFechaFin());
        dto.setAnios(e.getAnios());
        dto.setMeses(e.getMeses());

        dto.setIdSueldo(e.getIdSueldo());
        dto.setIdTipoContratacion(e.getIdTipoContratacion());
        dto.setIdMotivoSeparacion(e.getIdMotivoSeparacion());
        dto.setMotivoSeparacion(e.getMotivoSeparacion());
        dto.setNivelPuesto(e.getNivelPuesto());

        // --------- Nombres para mostrar en la tabla ----------
        dto.setTipoEmpleoNombre(
                e.getIdTipoEmpleo() != null
                        ? catEmpleoRepo.findById(e.getIdTipoEmpleo())
                        .map(x -> x.getDesTipoEmpleo())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setSectorGeneralNombre(
                e.getIdSectorGeneral() != null
                        ? catSectorGeneralRepo.findById(e.getIdSectorGeneral())
                        .map(x -> x.getDesSectorGeneral())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setSectorNombre(
                e.getIdSector() != null
                        ? catSectorRepo.findById(e.getIdSector())
                        .map(x -> x.getNomSector())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setLaboralNombre(
                e.getIdLaboral() != null
                        ? catLaboralRepo.findById(e.getIdLaboral())
                        .map(x -> x.getDesLaboral())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setSueldoNombre(
                e.getIdSueldo() != null
                        ? catSueldoRepo.findById(e.getIdSueldo())
                        .map(x -> x.getRango())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setTipoContratacionNombre(
                e.getIdTipoContratacion() != null
                        ? catTipoContratacionRepo.findById(e.getIdTipoContratacion())
                        .map(x -> x.getDesTipo())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setMotivoSeparacionNombre(
                e.getIdMotivoSeparacion() != null
                        ? catMotivoSepRepo.findById(e.getIdMotivoSeparacion())
                        .map(x -> x.getDesMotivo())
                        .orElse("No definido")
                        : "No definido"
        );

        dto.setNivelPuestoNombre(
                e.getNivelPuesto() != null
                        ? "Nivel " + e.getNivelPuesto()
                        : "No definido"
        );

        return dto;
    }
}
