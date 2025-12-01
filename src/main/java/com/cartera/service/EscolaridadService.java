package com.cartera.service;

import com.cartera.dto.EscolaridadDTO;
import com.cartera.model.DtEscolaridad;
import com.cartera.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaridadService {

    private final DtEscolaridadRepository escolaridadRepo;

    private final CatEstudioRepository catEstudioRepo;
    private final CatGradoRepository catGradoRepo;
    private final CatLicenciaturaRepository catLicRepo;
    private final CatInstitucionRepository catInstRepo;
    private final CatModalidadRepository catModalRepo;

    // =======================
    // GUARDAR / ACTUALIZAR
    // =======================
    public void guardar(EscolaridadDTO dto) {

        DtEscolaridad e = new DtEscolaridad();

        // Si viene ID → es edición
        if(dto.getIdEscolaridad() != null) {
            e = escolaridadRepo.findById(dto.getIdEscolaridad())
                    .orElse(new DtEscolaridad());
        }

        e.setIdPersona(dto.getIdPersona());

        // Catálogos (IDs)
        e.setIdEstudio(dto.getIdEstudio());
        e.setIdGrado(dto.getIdGrado());
        e.setIdLicenciatura(dto.getIdLicenciatura());
        e.setIdInstitucion(dto.getIdInstitucion());
        e.setIdModalidad(dto.getIdModalidad());

        // Otros campos
        e.setOtraCarrera(dto.getOtraCarrera());
        e.setAnioInicio(dto.getAnioInicio());
        e.setAnioFin(dto.getAnioFin());

        // Cálculo años cursados
        if (dto.getAnioInicio() != null && dto.getAnioFin() != null) {
            int anios = dto.getAnioFin() - dto.getAnioInicio();
            e.setAniosCursados(String.valueOf(anios));
        } else {
            e.setAniosCursados(null);
        }

        escolaridadRepo.save(e);
    }

    // =======================
    // LISTAR POR PERSONA
    // =======================
    public List<EscolaridadDTO> listarPorPersona(Long idPersona) {
        return escolaridadRepo.findByIdPersona(idPersona)
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    // =======================
    // BUSCAR POR ID
    // =======================
    public EscolaridadDTO buscarPorId(Integer idEscolaridad) {

        DtEscolaridad e = escolaridadRepo.findById(idEscolaridad)
                .orElseThrow(() -> new RuntimeException("Escolaridad no encontrada"));

        return convertirADTO(e);
    }

    // =======================
    // CONVERTIR ENTIDAD → DTO
    // =======================
    private EscolaridadDTO convertirADTO(DtEscolaridad e) {

        EscolaridadDTO dto = new EscolaridadDTO();

        dto.setIdEscolaridad(e.getIdEscolaridad());
        dto.setIdPersona(e.getIdPersona());

        dto.setIdEstudio(e.getIdEstudio());
        dto.setIdGrado(e.getIdGrado());
        dto.setIdLicenciatura(e.getIdLicenciatura());
        dto.setIdInstitucion(e.getIdInstitucion());
        dto.setIdModalidad(e.getIdModalidad());

        dto.setOtraCarrera(e.getOtraCarrera());
        dto.setAnioInicio(e.getAnioInicio());
        dto.setAnioFin(e.getAnioFin());
        dto.setAniosCursados(e.getAniosCursados());

        // ======================================
        // 🔥 CONVERSIÓN DE ID → NOMBRE CATÁLOGO
        // ======================================

        // Estudio concluido
        dto.setEstudioNombre(
                e.getIdEstudio() != null ?
                        catEstudioRepo.findById(e.getIdEstudio())
                                .map(x -> x.getDescEstudio())
                                .orElse("No definido")
                        : "No definido"
        );

        // Documento obtenido
        dto.setGradoNombre(
                e.getIdGrado() != null ?
                        catGradoRepo.findById(e.getIdGrado())
                                .map(x -> x.getDesGrado())
                                .orElse("No definido")
                        : "No definido"
        );

        // Institución
        dto.setInstitucionNombre(
                e.getIdInstitucion() != null ?
                        catInstRepo.findById(e.getIdInstitucion())
                                .map(x -> x.getDesInstitucion())
                                .orElse("No definido")
                        : "No definido"
        );

        // Modalidad
        dto.setModalidadNombre(
                e.getIdModalidad() != null ?
                        catModalRepo.findById(e.getIdModalidad())
                                .map(x -> x.getDesModalidad())
                                .orElse("No definido")
                        : "No definido"
        );

        // Licenciatura
        dto.setLicenciaturaNombre(
                e.getIdLicenciatura() != null ?
                        catLicRepo.findById(e.getIdLicenciatura())
                                .map(x -> x.getDesLicenciatura())
                                .orElse("No definido")
                        : "No definido"
        );

        return dto;
    }


    // =======================
    // ELIMINAR
    // =======================
    public void eliminar(Integer id) {
        escolaridadRepo.deleteById(id);
    }
}
