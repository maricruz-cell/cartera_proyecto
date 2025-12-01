package com.cartera.service.impl;

import com.cartera.dto.CapacitacionDTO;
import com.cartera.model.DtCapacitacion;
import com.cartera.repository.DtCapacitacionRepository;
import com.cartera.repository.VwCapacitacionCompletaRepository;
import com.cartera.service.CapacitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapacitacionServiceImpl implements CapacitacionService {

    private final DtCapacitacionRepository repo;
    private final VwCapacitacionCompletaRepository vwRepo;

    // ======================== LISTAR ============================
    @Override
    public List<CapacitacionDTO> listarPorPersona(Long idPersona) {

        return repo.findByIdPersona(idPersona)
                .stream()
                .map(c -> {
                    CapacitacionDTO dto = new CapacitacionDTO();

                    dto.setIdCapacitacion(c.getIdCapacitacion());
                    dto.setIdPersona(c.getIdPersona());
                    dto.setDescCurso(c.getDescCurso());
                    dto.setIdAreaCapacitacion(c.getIdAreaCapacitacion());
                    dto.setIdTipoCurso(c.getIdTipoCurso());
                    dto.setIdHorasCurso(c.getIdHorasCurso());
                    dto.setIdInstitucion(c.getIdInstitucion());
                    dto.setDescFechaInicio(c.getDescFechaInicio());
                    dto.setDescFechaFin(c.getDescFechaFin());

                    return dto;
                }).collect(Collectors.toList());
    }

    // ======================== GUARDAR ============================
    @Override
    public void guardarCapacitacion(CapacitacionDTO dto) {

        DtCapacitacion c = new DtCapacitacion();

        c.setIdCapacitacion(dto.getIdCapacitacion());
        c.setIdPersona(dto.getIdPersona());
        c.setDescCurso(dto.getDescCurso());
        c.setIdAreaCapacitacion(dto.getIdAreaCapacitacion());
        c.setIdTipoCurso(dto.getIdTipoCurso());
        c.setIdHorasCurso(dto.getIdHorasCurso());
        c.setIdInstitucion(dto.getIdInstitucion());
        c.setDescFechaInicio(dto.getDescFechaInicio());
        c.setDescFechaFin(dto.getDescFechaFin());

        repo.save(c);
    }

    // ======================== BUSCAR POR ID ============================
    @Override
    public CapacitacionDTO buscarPorId(Integer idCap) {

        var vista = vwRepo.findById(idCap)
                .orElseThrow(() -> new RuntimeException("Capacitación no encontrada"));

        CapacitacionDTO dto = new CapacitacionDTO();

        dto.setIdCapacitacion(vista.getIdCapacitacion());
        dto.setIdPersona(vista.getIdPersona());
        dto.setDescCurso(vista.getDescCurso());
        dto.setDescFechaInicio(
                vista.getDescFechaInicio() != null ? LocalDate.parse(vista.getDescFechaInicio()) : null
        );

        dto.setDescFechaFin(
                vista.getDescFechaFin() != null ? LocalDate.parse(vista.getDescFechaFin()) : null
        );


        // Nuevos campos de la vista
        dto.setArea(vista.getArea());
        dto.setTipoCurso(vista.getTipoCurso());
        dto.setInstitucion(vista.getInstitucion());
        dto.setHoras(vista.getHoras());

        return dto;
    }


    // ======================== ELIMINAR ============================
    @Override
    public void eliminar(Integer idCapacitacion) {
        repo.deleteById(idCapacitacion);
    }
}
